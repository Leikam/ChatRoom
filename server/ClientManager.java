package ChatRoom.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ClientManager {

    private Map<String, Socket> clients;

    public ClientManager() {
        this.clients = new HashMap<>();
    }

    public void add(Socket client) {
        getWriter(client).println("Ваше имя?\n");
        final String name = getReader(client).nextLine();
        System.out.println("Записываем как = " + name);
        this.clients.put(name != null ? name : "unknown" + clients.size(), client);
    }

    private Scanner getReader(Socket socket) {
        try {
            final InputStream is = socket.getInputStream();
            return new Scanner(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private PrintStream getWriter(Socket socket) {
        try {
            final OutputStream os = socket.getOutputStream();
            return new PrintStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void remove(Socket client) {
        this.clients.values().remove(client);
    }

    public void sentToRoom(Socket socket, String message) {
        this.clients.keySet().forEach(
            name -> {
                try {
                    final PrintStream printStream = new PrintStream(this.clients.get(name).getOutputStream());

                    printStream.println();
                    printStream.printf(
                        "> %s гвоорит: %s",
                        this.clients.entrySet()
                                    .stream()
                                    .filter(entry -> entry.getValue().equals(socket))
                                    .findFirst()
                                    .get()
                                    .getKey(),
                        message
                    );
                    printStream.println();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        );
    }
}
