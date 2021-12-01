package ru.kpfu.itis.sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {

    private Socket clientSocket;

    private final String HOST = "localhost";
    private final int PORT = 8888;

    private PrintWriter out;

    public void connect() {
        try {
            clientSocket = new Socket(HOST, PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void sendMessage(int message) {
        out.println(message);
    }
}
