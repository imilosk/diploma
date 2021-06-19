package fri.diplomska.diplomska.services;

import com.corundumstudio.socketio.SocketIONamespace;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import fri.diplomska.diplomska.helpers.DockerHelpers;
import fri.diplomska.diplomska.helpers.FileHelpers;
import fri.diplomska.diplomska.models.DockerImage;
import fri.diplomska.diplomska.repositories.DockerImageRepositoryImpl;
import fri.diplomska.diplomska.websockets.ImageBuildProgressModule;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
     * @param file The zip file that contains Dockerfile and it's needed files to build
     * @param imageName The image name
     * @param imageTag The image tag (e.g. v1)
     * @param additionalArgs Additional docker build args
     * @throws InterruptedException, DockerException, IOException
     */
    public void buildImage(MultipartFile file, String imageName, String imageTag, String additionalArgs) throws
            InterruptedException, DockerException, IOException {

        imageName = imageName + ":" + imageTag;

        String filePath = this.fileHelpers.createTempDirectory();
        this.fileHelpers.unzipFile(file, filePath);

//        DockerClient.BuildParam param = DockerClient.BuildParam.create("target", "dev-env");
        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();

        final String returnedImageId = this.dockerClient.build(
            Paths.get(filePath), imageName, progressMessage -> this.dockerHelpers.sendBuildProgress(progressMessage,
                        imageIdFromMessage, this.socketIONamespace, "imageProgress"));

        long imageSize = this.dockerClient.inspectImage(returnedImageId).size();
        this.dockerImageRepositoryImpl.upsert(imageName, imageTag, imageSize, returnedImageId);

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

}
