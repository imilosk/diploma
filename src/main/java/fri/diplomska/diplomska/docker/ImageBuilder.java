package fri.diplomska.diplomska.docker;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ProgressMessage;
import fri.diplomska.diplomska.websockets.ChatModule;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

public class ImageBuilder {

    private ChatModule chatModule;

    public ImageBuilder(ChatModule chatModule) {
        this.chatModule = chatModule;
    }

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

                        chatModule.getNamespace().getBroadcastOperations().sendEvent("chat",
                                "test");

                        if (progressMessage.status() != null && !progressMessage.status().isEmpty()) {
                            chatModule.getNamespace().getBroadcastOperations().sendEvent("chat",
                                    progressMessage.status());
                        }
                    }
                });
        return returnedImageId;
    }

}
