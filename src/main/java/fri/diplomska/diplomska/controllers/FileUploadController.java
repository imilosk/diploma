package fri.diplomska.diplomska.controllers;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ImageInfo;
import fri.diplomska.diplomska.docker.ImageBuilder;
import fri.diplomska.diplomska.helpers.Helper;
import fri.diplomska.diplomska.repository.ImageRepository;
import fri.diplomska.diplomska.requestModels.DockerImage;
import fri.diplomska.diplomska.requestModels.UploadImageRequest;
import fri.diplomska.diplomska.websockets.ChatModule;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class FileUploadController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ChatModule chatModule;

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/app/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<String> index(@Valid UploadImageRequest request) {
        try {

            String imageName = request.getImageName();
            String imageTag = request.getImageTag();
            String additionalArgs = request.getAdditionalArgs();
            MultipartFile file = request.getFile();

            final DockerClient docker = DefaultDockerClient.fromEnv().
                    build();

            // create a random UUID for folder name
            String folderName = UUID.randomUUID().toString();

            // path to the file
            String fileFolder = Helper.getProjectPath() + folderName;

            // create a new directory on the server FS
            Files.createDirectory(Paths.get(fileFolder));
            // save the zip file to the server FS
            Path filePathToSave = Paths.get(fileFolder, "image.zip");

            byte[] bytes = file.getBytes();
            Files.write(filePathToSave, bytes);

            // unzip the files
            new ZipFile(filePathToSave.toString()).extractAll(fileFolder);

            ImageBuilder imageBuilder = new ImageBuilder(chatModule);
            String imageId = imageBuilder.build(fileFolder, imageName, imageTag, additionalArgs);

            ImageInfo imageInfo = docker.inspectImage(imageId);

            FileUtils.deleteDirectory(new File(fileFolder));

            DockerImage dockerImage = new DockerImage();
            dockerImage.setName(imageName);
            dockerImage.setSize(imageInfo.size());
            dockerImage.setImageId(imageId);
            dockerImage.setTag(imageTag);
            imageRepository.save(dockerImage);


        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
