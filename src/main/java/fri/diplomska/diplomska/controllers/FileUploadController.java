package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.docker.ImageBuilder;
import fri.diplomska.diplomska.helpers.Helper;
import fri.diplomska.diplomska.kubernetes.Deployer;
import net.lingala.zip4j.ZipFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class FileUploadController {

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/app/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<String> index(@RequestParam("imageName") String imageName,
                                        @RequestParam("imageTag") String imageTag,
                                        @RequestParam("additionalArgs") String additionalArgs,
                                        @RequestParam("file") MultipartFile file) {
        try {

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
            System.out.println(imageId);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
