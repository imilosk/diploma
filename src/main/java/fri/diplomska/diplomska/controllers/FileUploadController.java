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
    @CrossOrigin(origins = "http://milos-diploma.tech:9090/")
    @RequestMapping(value = "/uploadDockerImage", method  = RequestMethod.GET )
    public ResponseEntity<String> index(@RequestParam("file") MultipartFile file) {
        try {
            int a = 0;
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
