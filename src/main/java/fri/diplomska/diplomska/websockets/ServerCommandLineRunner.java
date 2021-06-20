package fri.diplomska.diplomska.websockets;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class ServerCommandLineRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public ServerCommandLineRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) {
        server.start();
    }
}