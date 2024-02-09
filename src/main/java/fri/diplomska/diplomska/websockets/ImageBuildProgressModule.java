package fri.diplomska.diplomska.websockets;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import fri.diplomska.diplomska.models.data.ImageBuildProgressDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImageBuildProgressModule {

    private static final Logger log = LoggerFactory.getLogger(ImageBuildProgressModule.class);

    private final SocketIONamespace namespace;

    public ImageBuildProgressModule(SocketIOServer server) {
        this.namespace = server.addNamespace("/imageProgress");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("imageProgress", ImageBuildProgressDataModel.class, onMessageReceived());
    }

    private DataListener<ImageBuildProgressDataModel> onMessageReceived() {
        return (client, data, ackSender) -> {
            System.out.println("Message received");
            log.debug("Client[{}] - Received message '{}'", client.getSessionId().toString(), data);
            this.namespace.getBroadcastOperations().sendEvent("imageProgress", data);
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            log.debug("Client[{}] - Connected to imageProgress module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.debug("Client[{}] - Disconnected from imageProgress module.", client.getSessionId().toString());
        };
    }

    public SocketIONamespace getNamespace() {
        return this.namespace;
    }

}