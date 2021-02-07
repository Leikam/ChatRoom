package ChatRoom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalServer {

    public static final int PORT = 1234;

    public static void main(String ...args) throws IOException {
        int port = PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        final ServerSocket serverSocket = new ServerSocket(port);
        final RoomManager roomManager = new RoomManager();

        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            final Client client = new Client(socket, roomManager);
            new Thread(client).start();
        }
    }

}
