package fri.diplomska.diplomska.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

public class ImageBuilder {

    public String build(String filePath, String imageName) throws Exception {
        final DockerClient docker = DefaultDockerClient.fromEnv().
                build();

        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
        final String returnedImageId = docker.build(
                Paths.get(filePath), imageName, message -> {
                    final String imageId = message.buildImageId();
                    if (imageId != null) {
                        imageIdFromMessage.set(imageId);
                    }
                });
        return returnedImageId;
    }

}
