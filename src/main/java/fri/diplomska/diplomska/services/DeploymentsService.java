package fri.diplomska.diplomska.services;

import fri.diplomska.diplomska.helpers.KubernetesHelpers;
import fri.diplomska.diplomska.models.Deployment;
import fri.diplomska.diplomska.models.DockerImage;
import fri.diplomska.diplomska.models.data.DeploymentDataModel;
import fri.diplomska.diplomska.models.data.K8sServiceDataModel;
import fri.diplomska.diplomska.repositories.DeploymentRepositoryImpl;
import fri.diplomska.diplomska.repositories.DockerImageRepositoryImpl;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component
public class DeploymentsService {

    private final KubernetesHelpers kubernetesHelpers;
    private final KubernetesClient k8sClient;
    private final DeploymentRepositoryImpl deploymentRepositoryImpl;
    private final DockerImageRepositoryImpl dockerImageRepositoryImpl;

    public DeploymentsService(KubernetesHelpers kubernetesHelpers, DeploymentRepositoryImpl deploymentRepository,
                              DockerImageRepositoryImpl dockerImageRepository) {
        this.kubernetesHelpers = kubernetesHelpers;
        this.deploymentRepositoryImpl = deploymentRepository;
        this.dockerImageRepositoryImpl = dockerImageRepository;
        this.k8sClient = new DefaultKubernetesClient();
    }

    /**
     *
     * @param deployment The K8s deployment data model
     * @param service The K8s service data model
     */
    public void deployService(DeploymentDataModel deployment, K8sServiceDataModel service) {
        DockerImage image = this.dockerImageRepositoryImpl.get("4329f1e7f119");
        deployment.setImageName("node_js");
        deployment.setImageTag("v1");

        int containerPort = 10006;
        deployment.setContainerPort(containerPort);
        deployment.setDeploymentName("mynodeproject");
        deployment.setImageId("4329f1e7f119");

        this.kubernetesHelpers.createOrUpdateK8sDeployment(this.k8sClient, deployment);
        this.kubernetesHelpers.createOrUpdateK8sService(this.k8sClient, service);
        this.deploymentRepositoryImpl.upsert(deployment, service);
    }

    /**
     * Returns a list of all deployments in the DB
     *
     * @return List of docker images
     */
    public Iterable<Deployment> getAllDeployments() {
        return this.deploymentRepositoryImpl.getAll();
    }

    /**
     * Deletes a deployment with the given name
     *
     * @param name The deployment name
     */
    public void deleteDeployment(String name, String namespace) throws Exception {
        this.kubernetesHelpers.deleteDeployment(this.k8sClient, name, namespace);
        String serviceName = name + "-service";
        this.kubernetesHelpers.deleteService(this.k8sClient, serviceName, namespace);
        this.deploymentRepositoryImpl.deleteByName(name);
    }

}
