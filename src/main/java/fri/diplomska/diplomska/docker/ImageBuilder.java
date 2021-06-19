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
import com.spotify.docker.client.messages.ProgressDetail;
import com.spotify.docker.client.messages.ProgressMessage;
import fri.diplomska.diplomska.websockets.ChatModule;
import fri.diplomska.diplomska.websockets.ResponseMessage;

import java.nio.file.Paths;
import java.util.Objects;
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

//        DockerClient.BuildParam param = DockerClient.BuildParam.create("target", "dev-env");

        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
        final String returnedImageId = docker.build(
                Paths.get(filePath), imageName, new ProgressHandler() {
                    @Override
                    public void progress(ProgressMessage progressMessage) throws DockerException {
                        final String imageId = progressMessage.buildImageId();
                        if (imageId != null) {
                            imageIdFromMessage.set(imageId);
                        }

                        ProgressDetail progressDetail = progressMessage.progressDetail();
                        String progress = progressMessage.progress();
                        String status = progressMessage.status();
                        String stream = progressMessage.stream();

                        if (status != null || progress != null || stream != null) {
                            String outputMessage = Objects.toString(stream);
                            ResponseMessage responseMessage = new ResponseMessage();
                            responseMessage.setMessage(outputMessage);
                            responseMessage.setProgress(progress);

                            chatModule.getNamespace().getBroadcastOperations().sendEvent("chat", responseMessage);
                        }
                    }
                });
        return returnedImageId;
    }

}
