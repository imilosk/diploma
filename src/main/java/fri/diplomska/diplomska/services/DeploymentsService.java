package fri.diplomska.diplomska.services;

import fri.diplomska.diplomska.helpers.KubernetesHelpers;
import fri.diplomska.diplomska.models.data.DeploymentDataModel;
import fri.diplomska.diplomska.models.data.K8sServiceDataModel;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component
public class DeploymentsService {

    private final KubernetesHelpers kubernetesHelpers;
    private final KubernetesClient k8sClient;

    public DeploymentsService(KubernetesHelpers kubernetesHelpers) {
        this.kubernetesHelpers = kubernetesHelpers;
        this.k8sClient = new DefaultKubernetesClient();
    }

    public void deployService(DeploymentDataModel deployment, K8sServiceDataModel service) {
        this.kubernetesHelpers.createK8sDeployment(this.k8sClient, deployment);
        this.kubernetesHelpers.createK8sService(this.k8sClient, service);
    }

}
