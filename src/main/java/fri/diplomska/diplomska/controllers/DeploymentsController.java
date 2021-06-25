package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.models.data.DeploymentDataModel;
import fri.diplomska.diplomska.services.KubernetesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DeploymentsController {

    private final KubernetesService kubernetesService;

    public DeploymentsController(KubernetesService kubernetesService) {
        this.kubernetesService = kubernetesService;
    }

    @RequestMapping(value = "/app/services", method = RequestMethod.POST)
    public ResponseEntity<String> index(@Valid DeploymentDataModel deploymentDataModel) {
        deploymentDataModel.setNamespace(null);
        try {
            this.kubernetesService.deployService(deploymentDataModel);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
