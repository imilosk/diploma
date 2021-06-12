package fri.diplomska.diplomska.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ProgressMessage;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

public class ImageBuilder {

    public String build(String filePath, String imageName, String imageTag, String additionalArgs) throws Exception {
        final DockerClient docker = DefaultDockerClient.fromEnv().
                build();

        imageName = imageName + ":" + imageTag;

        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
        final String returnedImageId = docker.build(
                Paths.get(filePath), imageName, new ProgressHandler() {
                    @Override
                    public void progress(ProgressMessage progressMessage) throws DockerException {
                        final String imageId = progressMessage.buildImageId();
                        if (imageId != null) {
                            imageIdFromMessage.set(imageId);
                        }
                    }
                });
        return returnedImageId;
    }

}
