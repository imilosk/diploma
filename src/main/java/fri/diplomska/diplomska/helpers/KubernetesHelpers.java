package fri.diplomska.diplomska.helpers;

import fri.diplomska.diplomska.models.data.DeploymentDataModel;
import fri.diplomska.diplomska.models.data.K8sServiceDataModel;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component
public class KubernetesHelpers {

    public void createK8sDeployment(KubernetesClient client, DeploymentDataModel deploymentDataModel) {
        String deploymentName = deploymentDataModel.getDeploymentName();
        String imageName = deploymentDataModel.getImageName() + ":" + deploymentDataModel.getImageTag();
        int containerPort = deploymentDataModel.getContainerPort();
        String namespace = deploymentDataModel.getNamespace();

        ServiceAccount fabric8 = new ServiceAccountBuilder().withNewMetadata().withName("fabric8").endMetadata().build();
        client.serviceAccounts().inNamespace(namespace).createOrReplace(fabric8);

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

    public void createK8sService(KubernetesClient client, K8sServiceDataModel service) {
        String namespace = service.getNamespace();
        String serviceName = service.getServiceName();
        String externalName = service.getDnsName();
        int servicePort = service.getServicePort();
        int containerPort = service.getDeploymentDataModel().getContainerPort();
        String deploymentName = service.getDeploymentDataModel().getDeploymentName();

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
