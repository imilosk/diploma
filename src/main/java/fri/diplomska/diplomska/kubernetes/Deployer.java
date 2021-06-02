package fri.diplomska.diplomska.kubernetes;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ImageInfo;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Deployer {

    public void deploy(String imageName, String deploymentName, int containerPort, int servicePort) throws DockerCertificateException, DockerException, InterruptedException {
        KubernetesClient client = new DefaultKubernetesClient();
        final DockerClient docker = DefaultDockerClient.fromEnv().
                build();

        ImageInfo imageInfo = docker.inspectImage("03abbbbaefc0");
        String testName = imageInfo.config().domainname();

        String namespace = "default";
        String serviceName = deploymentName + "service";
        String externalName = "my." + deploymentName + ".app";

        createK8sDeployment(client, imageName, deploymentName, containerPort, namespace);
        createK8sService(client, serviceName, deploymentName, externalName, containerPort, servicePort, namespace);
    }

    private void createK8sDeployment(KubernetesClient client, String imageName, String deploymentName,
                                     int containerPort, String namespace) {
        ServiceAccount fabric8 = new ServiceAccountBuilder().withNewMetadata().withName("fabric8").endMetadata().build();
        client.serviceAccounts().inNamespace("default").createOrReplace(fabric8);

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
        System.out.println("Created deployment: " + deployment);
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

        System.out.println("Created service:" + createdSvc);
    }

}
