package fri.diplomska.diplomska.helpers;

import com.corundumstudio.socketio.SocketIONamespace;
import com.spotify.docker.client.messages.ProgressMessage;
import fri.diplomska.diplomska.models.data.ImageBuildProgressDataModel;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DockerHelpers {

    /**
     * Receives the progress of the docker build and broadcasts it to the socket.io channel
     *
     * @param progressMessage The progress message
     * @param imageIdFromMessage The image id
     */
    public void sendBuildProgress(ProgressMessage progressMessage, AtomicReference<String> imageIdFromMessage,
                                   SocketIONamespace socketIONamespace, String socketIOChannel) {
        final String imageId = progressMessage.buildImageId();
        if (imageId != null) {
            imageIdFromMessage.set(imageId);
        }

//        ProgressDetail progressDetail = progressMessage.progressDetail();
        String progress = progressMessage.progress();
        String status = progressMessage.status();
        String stream = progressMessage.stream();

        if (status != null || progress != null || stream != null) {
            String outputMessage = Objects.toString(stream);
            ImageBuildProgressDataModel responseMessage = new ImageBuildProgressDataModel();
            responseMessage.setMessage(outputMessage);
            responseMessage.setProgress(progress);

            socketIONamespace.getBroadcastOperations().sendEvent(socketIOChannel, responseMessage);
        }
    }

}
