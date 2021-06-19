package fri.diplomska.diplomska.services;

import com.corundumstudio.socketio.SocketIONamespace;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ImageInfo;
import fri.diplomska.diplomska.helpers.DockerHelpers;
import fri.diplomska.diplomska.models.DockerImage;
import fri.diplomska.diplomska.repositories.DockerImageRepository;
import fri.diplomska.diplomska.websockets.ImageBuildProgressModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DockerService {

    private final SocketIONamespace socketIONamespace;
    private final DockerImageRepository imageRepository;
    private final DockerHelpers dockerHelpers;

    private final DockerClient dockerClient;

    @Autowired
    public DockerService(ImageBuildProgressModule imageBuildProgressModule, DockerImageRepository imageRepository, DockerHelpers dockerHelpers) throws DockerCertificateException {
        this.socketIONamespace = imageBuildProgressModule.getNamespace();
        this.imageRepository = imageRepository;
        this.dockerHelpers = dockerHelpers;
        this.dockerClient = DefaultDockerClient.fromEnv().build();
    }

    /**
     * @param filePath The filepath of the Dockerfile
     * @param imageName The image name
     * @param imageTag The image tag (e.g. v1)
     * @param additionalArgs Additional docker build args
     * @throws InterruptedException, DockerException, IOException
     */
    public void buildImage(String filePath, String imageName, String imageTag, String additionalArgs) throws
            InterruptedException, DockerException, IOException {

        imageName = imageName + ":" + imageTag;

//        DockerClient.BuildParam param = DockerClient.BuildParam.create("target", "dev-env");
        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();

        final String returnedImageId = this.dockerClient.build(
            Paths.get(filePath), imageName, progressMessage -> dockerHelpers.sendBuildProgress(progressMessage,
                        imageIdFromMessage, this.socketIONamespace, "imageProgress"));

        ImageInfo imageInfo = this.dockerClient.inspectImage(returnedImageId);
        DockerImage existingDockerImage = this.imageRepository.findByImageId(returnedImageId);

        if (existingDockerImage == null) {
            DockerImage dockerImage = new DockerImage();
            dockerImage.setName(imageName);
            dockerImage.setSize(imageInfo.size());
            dockerImage.setImageId(returnedImageId);
            dockerImage.setTag(imageTag);
            this.imageRepository.save(dockerImage);
        } else {
            existingDockerImage.setTag(imageTag);
            existingDockerImage.setName(imageName);
            this.imageRepository.save(existingDockerImage);
        }

    }


    /**
     * Returns a list of all Docker images in the DB
     *
     * @return List of docker images
     */
    public Iterable<DockerImage> getAllImages() {
        return imageRepository.findAll();
    }

}
