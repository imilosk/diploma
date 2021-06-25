package fri.diplomska.diplomska.services;

import fri.diplomska.diplomska.models.data.DeploymentDataModel;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KubernetesService {

    @Bean
    public KubernetesService KubernetesService() {
        return new KubernetesService();
    }

    public void deployService(DeploymentDataModel deployment) {
        KubernetesClient client = new DefaultKubernetesClient();

        String namespace = "default";

        deployment.setNamespace(namespace);
        this.createK8sDeployment(client, deployment);

        String serviceName = deployment.getDeploymentName() + "service";
        String externalName = "my." + deployment.getDeploymentName() + ".app";

        this.createK8sService(client, serviceName, deployment.getDeploymentName(), externalName,
                deployment.getContainerPort(),
                deployment.getServicePort(), namespace);
    }

    private void createK8sDeployment(KubernetesClient client, DeploymentDataModel deploymentDataModel) {
        ServiceAccount fabric8 = new ServiceAccountBuilder().withNewMetadata().withName("fabric8").endMetadata().build();
        client.serviceAccounts().inNamespace("default").createOrReplace(fabric8);

        String deploymentName = deploymentDataModel.getDeploymentName();
        String imageName = deploymentDataModel.getImageName() + ":" + deploymentDataModel.getImageTag();
        int containerPort = deploymentDataModel.getContainerPort();
        String namespace = deploymentDataModel.getNamespace();

        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                .withName(deploymentName)
                .endMetadata()
                .withNewSpec()
                .withReplicas(1)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels("app", deploymentName)
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(deploymentName)
                .withImage(imageName)
                .addNewPort()
                .withContainerPort(containerPort)
                .endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .withNewSelector()
                .addToMatchLabels("app", deploymentName)
                .endSelector()
                .endSpec()
                .build();

        deployment = client.apps().deployments().inNamespace(namespace).createOrReplace(deployment);
    }

    private void createK8sService(KubernetesClient client, String serviceName, String deploymentName,
                                  String externalName, int containerPort, int servicePort, String namespace) {
        Service createdSvc = client.services().inNamespace(namespace).createOrReplaceWithNew()
                .withNewMetadata()
                .withName(serviceName)
                .endMetadata()
                .withNewSpec()
                .withType("LoadBalancer")
                .withExternalName(externalName)
                .addNewPort()
                .withName(Integer.toString(servicePort))
                .withProtocol("TCP")
                .withPort(servicePort)
                .withTargetPort(new IntOrString(containerPort))
                .endPort()
                .addToSelector("app", deploymentName)
                .endSpec()
                .withNewStatus()
                .withNewLoadBalancer()
                .addNewIngress()
                .endIngress()
                .endLoadBalancer()
                .endStatus()
                .done();

    }
}
