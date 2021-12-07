package ru.kpfu.itis.sockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageType;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {

    private Socket clientSocket;

    private final String HOST = "localhost";
    private final int PORT = 8888;

    private PrintWriter out;

    public void connect(String nickname) {
        try {
            clientSocket = new Socket(HOST, PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            Message message = new Message();
            message.setType(MessageType.CONNECT);
            message.addHeader("nickname", nickname);
            sendMessage(message);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void sendMessage(int message) {
        out.println(message);
    }

    public void sendMessage(Message message) {
        try {
            String jsonMessage = new ObjectMapper().writeValueAsString(message);
            System.out.println(jsonMessage);
            out.println(jsonMessage);
        } catch (JsonProcessingException e) {
            //console log
        }
    }
}
