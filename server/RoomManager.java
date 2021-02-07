package ChatRoom.server;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class RoomManager {

    private final Set<IClient> clients;

    public RoomManager() {
        this.clients = new HashSet<>();
    }

    public boolean add(IClient client) {
        System.out.println("Записываем как = " + client.getName());
        return this.clients.add(client);
    }

    public boolean remove(IClient client) {
        return this.clients.remove(client);
    }

    public int size() {
        return this.clients.size();
    }

    public void sentToRoom(String message, IClient cc) {
        this.clients.forEach(client -> {
            final PrintStream writer = client.getWriter();
            writer.println();
            writer.printf("> %s гвоорит: %s", cc.getName(), message);
            writer.println();
        });
    }
}
