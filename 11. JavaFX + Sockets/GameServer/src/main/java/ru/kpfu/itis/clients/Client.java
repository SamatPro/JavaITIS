package ru.kpfu.itis.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.sockets.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread {

    private String nickname;

    private Socket client;

    // поток символов, которые мы отправляем клиенту
    private PrintWriter toClient;

    // поток символов, которые мы читаем от клиента
    private BufferedReader fromClient;

    private GameServer gameServer;

    public Client(Socket client) {
        gameServer = GameServer.getInstance();
        this.client = client;
        try {
            // обернули потоки байтов в потоки символов
            this.toClient = new PrintWriter(client.getOutputStream(), true);
            this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    // мы в любое время можем получить сообщение от клиента
    // поэтому чтение сообщения от клиента должно происходить в побочном потоке
    @Override
    public void run() {
        while (true) {
            // прочитали сообщение от клиента
            String messageFromClient;

            try {
                messageFromClient = fromClient.readLine();
                if (messageFromClient != null) {
                    System.out.println(nickname + ": " + messageFromClient);

                    Message message = new ObjectMapper().readValue(messageFromClient, Message.class);
                    System.out.println(message.getHeaders());

                    switch (message.getType()) {
                        case CONNECT: {
                            this.nickname = message.getHeader("nickname");
                            System.out.println(nickname);
                            //todo уведомляем остальных клиентов
                            break;
                        }
                        case CHAT: {
                            List<Client> clients = gameServer.getClients();
                            message.setBody(nickname + ": " + message.getBody());

                            for (Client client: clients) {
                                if (this != client){
                                    System.out.println(client.nickname);
                                    client.sendMessage(message);
                                }
                            }
                            break;
                        }
                        case ACTION: {
                            List<Client> clients = new ArrayList<>(gameServer.getClients());
                            System.out.println(clients.toString());
                            clients.remove(this);
                            for (Client client: clients) {
                                client.sendMessage(message);
                            }
                            break;
                        }

                    }

                }
            } catch (IOException e) {
                //
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            String jsonMessage = new ObjectMapper().writeValueAsString(message);
            System.out.println("SEND messageTO: " + nickname + " " + jsonMessage);
            toClient.println(jsonMessage);
        } catch (JsonProcessingException e) {
            //console log
        }
    }
}
