package ChatRoom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class LocalServer {

    public static final int PORT = 1234;

    public static void main(String[] args) throws IOException {

        final Optional<Integer> port = Optional.of(Integer.parseInt(args[0]));
        final ServerSocket serverSocket = new ServerSocket(port.orElse(PORT));
        final ClientManager clientManager = new ClientManager();

        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            clientManager.add(socket);

            final Client client = new Client(socket, clientManager);
            new Thread(client).start();
        }
    }

}
