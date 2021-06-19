package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.kubernetes.Deployer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeploymentsController {

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/app/services", method = RequestMethod.POST)
    public ResponseEntity<String> index() {
        try {
            String imageId = "nodejs_test:v1";
            String deploymentName = "diplomska";
            int containerPort = 10006;
            int servicePort = 9999;

            Deployer deployer = new Deployer();
            deployer.deploy(imageId, deploymentName, containerPort, servicePort);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}