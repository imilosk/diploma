package fri.diplomska.diplomska.models.data;

import fri.diplomska.diplomska.models.request.DeploymentRequestDataModel;

public class K8sServiceDataModel {

    public K8sServiceDataModel() { }

    public K8sServiceDataModel(DeploymentRequestDataModel request) {
        this.servicePort = request.getServicePort();
        this.dnsName = request.getServiceDnsName();
    }

    private String serviceName;
    private DeploymentDataModel deploymentDataModel;
    private String dnsName;
    private int servicePort;
    private String namespace;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public DeploymentDataModel getDeploymentDataModel() {
        return deploymentDataModel;
    }

    public void setDeploymentDataModel(DeploymentDataModel deploymentDataModel) {
        this.deploymentDataModel = deploymentDataModel;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
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
