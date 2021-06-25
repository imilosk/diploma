package fri.diplomska.diplomska.models.data;

import javax.validation.constraints.NotBlank;

public class DeploymentDataModel {

    @NotBlank(message = "Image name is mandatory")
    private String imageName;

    @NotBlank(message = "Image tag is mandatory")
    private String imageTag;

    @NotBlank(message = "Deployment name is mandatory")
    private String deploymentName;

    private int containerPort;

    private int servicePort;

    private String namespace;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageTag() {
        return imageTag;
    }

    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public int getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(int containerPort) {
        this.containerPort = containerPort;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

}
