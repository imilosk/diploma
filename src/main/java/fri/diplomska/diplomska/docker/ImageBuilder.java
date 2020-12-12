package fri.diplomska.diplomska.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ProgressMessage;
import fri.diplomska.diplomska.helpers.Helper;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

public class ImageBuilder {

    public String build(String filePath) throws Exception {
        final DockerClient docker = DefaultDockerClient.fromEnv().build();
        
        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
        final String returnedImageId = docker.build(
                Paths.get(filePath), "diplomska:v3", new ProgressHandler() {
                    @Override
                    public void progress(ProgressMessage message) {
                        final String imageId = message.buildImageId();
                        if (imageId != null) {
                            imageIdFromMessage.set(imageId);
                        }
                    }
                });
        return returnedImageId;
    }

}
