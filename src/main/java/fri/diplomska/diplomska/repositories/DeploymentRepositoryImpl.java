package fri.diplomska.diplomska.repositories;

import fri.diplomska.diplomska.models.Deployment;
import fri.diplomska.diplomska.models.data.DeploymentDataModel;
import fri.diplomska.diplomska.models.data.K8sServiceDataModel;
import org.springframework.stereotype.Component;

@Component
public class DeploymentRepositoryImpl {

    private final DeploymentRepository deploymentRepository;

    public DeploymentRepositoryImpl(DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    /**
     * Upsert docker image in DB
     *
     * @param deploymentDataModel The Deployment data model
     */
    public void upsert(DeploymentDataModel deploymentDataModel, K8sServiceDataModel serviceDataModel) {
        Deployment deployment = this.deploymentRepository.findByName(deploymentDataModel.getDeploymentName());

        if (deployment == null) {
            deployment = new Deployment();
            deployment.setName(deploymentDataModel.getDeploymentName());
        }

        deployment.setImageName(deploymentDataModel.getImageName());
        deployment.setImageTag(deploymentDataModel.getImageTag());
        deployment.setContainerPort(deploymentDataModel.getContainerPort());
        deployment.setServicePort(serviceDataModel.getServicePort());
        deployment.setReplicas(deploymentDataModel.getReplicas());
        deployment.setNamespace(deploymentDataModel.getNamespace());
        this.deploymentRepository.save(deployment);
    }

    /**
     * Returns a list of all deployments in the DB
     *
     * @return List of docker images
     */
    public Iterable<Deployment> getAll() {
        return this.deploymentRepository.findAll();
    }

    /**
     * Get a Docker image by image id
     *
     * @param name The deployment name
     * @return Returns a DockerImage object
     */
    public Deployment get(String name) {
        return this.deploymentRepository.findByName(name);
    }

    /**
     * Deletes a deployment with the given name
     *
     * @param name The deployment to delete
     */
    public void deleteByName(String name) {
        Deployment deployment = this.get(name);
        this.deploymentRepository.delete(deployment);
    }

}
