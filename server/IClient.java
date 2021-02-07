package ChatRoom.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public interface IClient {
    String getName();
    Socket getSocket();

    PrintStream getWriter();
    Scanner getReader();
}
