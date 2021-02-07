package ChatRoom.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable, IClient {

    private Socket socket;
    private RoomManager roomManager;
    private Scanner reader;
    private PrintStream writer;
    private String name;

    public Client(Socket socket, RoomManager roomManager) throws IOException {
        this.socket = socket;
        this.roomManager = roomManager;
        this.reader = new Scanner(this.socket.getInputStream());
        this.writer = new PrintStream(this.socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            System.out.println("Чат открыт");

            writer.println("Введите ваше имя, пожалуйса:");
            this.name = reader.nextLine();
            if (name == null || name.trim().equals("")) {
                name = "Anonymous_" + roomManager.size();
            }

            this.roomManager.add(this);

            writer.printf("\nВы вошли в чат как (%s), напишите что-нибудь..\n", this.name);

            while (reader.hasNext()) {
                final String message = reader.nextLine();

                if ("q".equals(message.trim())) {
                    break;
                }

                roomManager.sentToRoom(message, this);
            }

            System.out.println("Cокет закрыт");
            reader.close();
            writer.close();
            this.socket.close();
            this.roomManager.remove(this);
        } catch (IOException e) {
            System.out.println("Соединение не удалось");
        }
    }

    @Override
    public PrintStream getWriter() {
        return this.writer;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }
}
