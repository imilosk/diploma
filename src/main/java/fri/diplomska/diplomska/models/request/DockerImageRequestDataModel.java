package fri.diplomska.diplomska.models.request;

import org.jvnet.hk2.annotations.Optional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DockerImageRequestDataModel {

    @NotBlank(message = "Image name is mandatory")
    private String imageName;

    @NotBlank(message = "Image tag is mandatory")
    private String imageTag;

    @Optional
    private String additionalArgs;

    @NotNull(message = "File must be provided")
    private MultipartFile file = null;

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

}
