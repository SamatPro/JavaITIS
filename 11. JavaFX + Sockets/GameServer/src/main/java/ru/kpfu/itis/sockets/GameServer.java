package ru.kpfu.itis.sockets;

import ru.kpfu.itis.clients.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer {

    private ServerSocket serverSocket;

    private List<Client> clients = new CopyOnWriteArrayList<>();

    private static GameServer gameServer;

    public static GameServer getInstance() {
        if (gameServer == null) {
            gameServer = new GameServer();
        }
        return gameServer;
    }

    private GameServer() {
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Socket client = serverSocket.accept();
                            Client player = new Client(client);
                            clients.add(player);
                            player.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            //console log
        }
    }

    public List<Client> getClients() {
        return clients;
    }
}
