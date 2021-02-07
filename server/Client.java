package ChatRoom.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {

    private Socket socket;
    private ClientManager roomManager;

    public Client(Socket socket, ClientManager roomManager) {
        this.socket = socket;
        this.roomManager = roomManager;
    }

    @Override
    public void run() {
        try {
            System.out.println("Чат открыт");


            final InputStream is = this.socket.getInputStream();
            final OutputStream out = this.socket.getOutputStream();

            Scanner reader = new Scanner(is);
            PrintStream writer = new PrintStream(out);

            writer.println("Чат открыт, напишите что-нибудь..");

            while (reader.hasNext()) {
                final String in = reader.nextLine();

                if ("q".equals(in.trim())) {
                    break;
                }

                roomManager.sentToRoom(socket, in);
            }

            System.out.println("Cокет закрыт");
            reader.close();
            writer.close();
            this.socket.close();
            this.roomManager.remove(socket);
        } catch (IOException e) {
            System.out.println("Соединение не удалось");
        }
    }
}
