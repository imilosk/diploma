package fri.diplomska.diplomska.models.data;

import fri.diplomska.diplomska.models.request.DockerImageRequestDataModel;
import org.springframework.web.multipart.MultipartFile;

public class DockerImageDataModel {

    public DockerImageDataModel() { }

    public DockerImageDataModel(DockerImageRequestDataModel request) {
        this.imageName = request.getImageName();
        this.imageTag = request.getImageTag();
        this.additionalArgs = request.getAdditionalArgs();
        this.file = request.getFile();
    }

    private String imageName;
    private String imageTag;
    private String additionalArgs;
    private MultipartFile file = null;
    private String imageId;
    private long imageSize;
    private String os;
    private String architecture;
    private String exposedPort;

    public String getAdditionalArgs() {
        return additionalArgs;
    }

    public void setAdditionalArgs(String additionalArgs) {
        this.additionalArgs = additionalArgs;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public long getImageSize() {
        return imageSize;
    }

    public void setImageSize(long imageSize) {
        this.imageSize = imageSize;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getExposedPort() {
        return exposedPort;
    }

    public void setExposedPort(String exposedPort) {
        this.exposedPort = exposedPort;
    }
}
