package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.docker.ImageBuilder;
import fri.diplomska.diplomska.helpers.Helper;
import fri.diplomska.diplomska.kubernetes.Deployer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class FileUploadController {

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/uploadDockerImage", method  = RequestMethod.POST )
    public ResponseEntity<String> index(@RequestParam("file") MultipartFile file) {
        try {
            ImageBuilder imageBuilder = new ImageBuilder();
            String fileFolder = Helper.getProjectPath() + "docker" + File.separator;
            String filePathToSave = fileFolder +  File.separator + "Dockerfile";
            file.transferTo(new File(filePathToSave));
            String imageId = imageBuilder.build(fileFolder);
            Deployer deployer = new Deployer();
            deployer.deploy(imageId);
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
