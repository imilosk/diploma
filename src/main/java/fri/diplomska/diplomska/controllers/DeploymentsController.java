package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.models.data.DeploymentDataModel;
import fri.diplomska.diplomska.models.data.K8sServiceDataModel;
import fri.diplomska.diplomska.models.request.DeploymentRequestDataModel;
import fri.diplomska.diplomska.services.DeploymentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DeploymentsController {

    private final DeploymentsService kubernetesService;

    public DeploymentsController(DeploymentsService kubernetesService) {
        this.kubernetesService = kubernetesService;
    }

    @RequestMapping(value = "/app/services", method = RequestMethod.POST)
    public ResponseEntity<String> index(@Valid DeploymentRequestDataModel request) {
        try {
            String namespace = "default";

            DeploymentDataModel deploymentDataModel = new DeploymentDataModel(request);
            deploymentDataModel.setNamespace(namespace);

            K8sServiceDataModel k8sServiceDataModel = new K8sServiceDataModel(request);
            k8sServiceDataModel.setDeploymentDataModel(deploymentDataModel);
            k8sServiceDataModel.setNamespace(namespace);

            String serviceName = deploymentDataModel.getDeploymentName() + "service";
            k8sServiceDataModel.setServiceName(serviceName);

            this.kubernetesService.deployService(deploymentDataModel, k8sServiceDataModel);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
