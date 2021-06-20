package fri.diplomska.diplomska.services;

import com.corundumstudio.socketio.SocketIONamespace;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import fri.diplomska.diplomska.helpers.DockerHelpers;
import fri.diplomska.diplomska.helpers.FileHelpers;
import fri.diplomska.diplomska.models.DockerImage;
import fri.diplomska.diplomska.models.data.DockerImageDataModel;
import fri.diplomska.diplomska.repositories.DockerImageRepositoryImpl;
import fri.diplomska.diplomska.websockets.ImageBuildProgressModule;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DockerService {

    private final DockerImageRepositoryImpl dockerImageRepositoryImpl;
    private final SocketIONamespace socketIONamespace;
    private final DockerClient dockerClient;
    private final DockerHelpers dockerHelpers;
    private final FileHelpers fileHelpers;

    public DockerService(DockerImageRepositoryImpl dockerImageRepositoryImpl,
                         ImageBuildProgressModule imageBuildProgressModule, DockerHelpers dockerHelpers,
                         FileHelpers fileHelpers) throws DockerCertificateException {
        this.dockerImageRepositoryImpl = dockerImageRepositoryImpl;
        this.socketIONamespace = imageBuildProgressModule.getNamespace();
        this.dockerClient = DefaultDockerClient.fromEnv().build();
        this.dockerHelpers = dockerHelpers;
        this.fileHelpers = fileHelpers;
    }

    /**
     * Builds the Dockerfile that is contained in the given zip
     *
     * @param imageDataModel The data model
     * @throws InterruptedException, DockerException, IOException
     */
    public void buildImage(DockerImageDataModel imageDataModel) throws
            InterruptedException, DockerException, IOException {

        String imageFullName = imageDataModel.getImageName() + ":" + imageDataModel.getImageTag();

        String filePath = this.fileHelpers.createTempDirectory();
        this.fileHelpers.unzipFile(imageDataModel.getFile(), filePath);

//        DockerClient.BuildParam param = DockerClient.BuildParam.create("target", "dev-env");
        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();

        final String returnedImageId = this.dockerClient.build(
            Paths.get(filePath), imageFullName, progressMessage -> this.dockerHelpers.sendBuildProgress(progressMessage,
                        imageIdFromMessage, this.socketIONamespace, "imageProgress"));

        long imageSize = this.dockerClient.inspectImage(returnedImageId).size();

        imageDataModel.setImageId(returnedImageId);
        imageDataModel.setImageSize(imageSize);

        this.dockerImageRepositoryImpl.upsert(imageDataModel);

        this.fileHelpers.deleteDirectory(filePath);
    }

    /**
     * Returns a list of all Docker images in the DB
     *
     * @return List of docker images
     */
    public Iterable<DockerImage> getAllImages() {
        return this.dockerImageRepositoryImpl.getAll();
    }

    /**
     * Deletes an image with the given image id
     *
     * @param imageId The Docker image id
     */
    public void deleteImage(String imageId) throws DockerException, InterruptedException {
        this.dockerClient.removeImage(imageId);
        this.dockerImageRepositoryImpl.deleteByImageId(imageId);
    }

}
