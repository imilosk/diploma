package fri.diplomska.diplomska.models.request;

import javax.validation.constraints.NotBlank;

public class DeploymentRequestDataModel {

    @NotBlank(message = "Image id is mandatory")
    private String imageId;

    @NotBlank(message = "Deployment name is mandatory")
    private String deploymentName;

    private int replicas;

    private int servicePort;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }
}
