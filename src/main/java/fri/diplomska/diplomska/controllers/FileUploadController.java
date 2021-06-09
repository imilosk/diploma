package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.docker.ImageBuilder;
import fri.diplomska.diplomska.helpers.Helper;
import fri.diplomska.diplomska.requestModels.UploadImageRequest;
import net.lingala.zip4j.ZipFile;
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

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/app/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<String> index(@Valid UploadImageRequest request) {
        try {

            String imageName = request.getImageName();
            String imageTag = request.getImageTag();
            String additionalArgs = request.getAdditionalArgs();
            MultipartFile file = request.getFile();

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

            ImageBuilder imageBuilder = new ImageBuilder();
            String imageId = imageBuilder.build(fileFolder, imageName, imageTag, additionalArgs);

            FileUtils.deleteDirectory(new File(fileFolder));

            System.out.println(imageId);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
