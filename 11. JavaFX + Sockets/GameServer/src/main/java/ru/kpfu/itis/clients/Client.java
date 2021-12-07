package ru.kpfu.itis.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.protocol.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private String nickname;

    private Socket client;

    // поток символов, которые мы отправляем клиенту
    private PrintWriter toClient;

    // поток символов, которые мы читаем от клиента
    private BufferedReader fromClient;

    public Client(Socket client) {
        this.client = client;
        try {
            this.toClient = new PrintWriter(client.getOutputStream(), true);
            this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            String messageFromClient;

            try {
                messageFromClient = fromClient.readLine();
                if (messageFromClient != null) {
                    System.out.println(messageFromClient);

                    Message message = new ObjectMapper().readValue(messageFromClient, Message.class);
                    System.out.println(message.getHeaders());

                    switch (message.getType()) {
                        case CONNECT: {
                            this.nickname = message.getHeader("nickname");
                            System.out.println(nickname);
                            break;
                        }
                    }

                }
            } catch (IOException e) {
                //
            }
        }
    }
}
