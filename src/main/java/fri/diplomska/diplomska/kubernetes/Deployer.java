package fri.diplomska.diplomska.kubernetes;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Deployer {

    public void deploy(String imageId) {
        KubernetesClient client = new DefaultKubernetesClient();

        createK8sDeployment(client);
        createK8sService(client);
    }

    private void createK8sDeployment(KubernetesClient client) {
        ServiceAccount fabric8 = new ServiceAccountBuilder().withNewMetadata().withName("fabric8").endMetadata().build();
        client.serviceAccounts().inNamespace("default").createOrReplace(fabric8);

        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                .withName("diplomska")
                .endMetadata()
                .withNewSpec()
                .withReplicas(1)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels("app", "diplomska")
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName("diplomska")
                .withImage("diplomska:v1")
                .addNewPort()
                .withContainerPort(8888)
                .endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .withNewSelector()
                .addToMatchLabels("app", "diplomska")
                .endSelector()
                .endSpec()
                .build();

        deployment = client.apps().deployments().inNamespace("default").createOrReplace(deployment);
        System.out.println("Created deployment: " + deployment);

    }

    private void createK8sService(KubernetesClient client) {
        Service createdSvc = client.services().inNamespace("default").createOrReplaceWithNew()
                .withNewMetadata().withName("diplomskaservice").endMetadata()
                .withNewSpec().withType("LoadBalancer").withExternalName("localhost")
                .addNewPort().withName("8080").withProtocol("TCP").withPort(8888).endPort()
                .addToSelector("app", "diplomska")
                .endSpec()
                .withNewStatus()
                .withNewLoadBalancer()
                .addNewIngress()
                .withIp("10.104.84.57")
                .endIngress()
                .endLoadBalancer()
                .endStatus()
                .done();

        System.out.println("Created service:" + createdSvc);
    }

}
