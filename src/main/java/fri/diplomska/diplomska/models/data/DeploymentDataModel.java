package fri.diplomska.diplomska.models.data;

import fri.diplomska.diplomska.models.request.DeploymentRequestDataModel;

public class DeploymentDataModel {

    public DeploymentDataModel() {
    }

    public DeploymentDataModel(DeploymentRequestDataModel request) {
        this.imageId = request.getImageId();
        this.deploymentName = request.getDeploymentName();
        this.replicas = request.getReplicas();
    }

    private String imageId;
    private String imageName;
    private String imageTag;
    private String deploymentName;
    private int containerPort;
    private int replicas;
    private String namespace;

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

    public int getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(int containerPort) {
        this.containerPort = containerPort;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

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
}
