package ChatRoom.server;

import java.io.PrintStream;
import java.net.Socket;

public interface IClient {
    String getName();
    Socket getSocket();

    PrintStream getWriter();
}
