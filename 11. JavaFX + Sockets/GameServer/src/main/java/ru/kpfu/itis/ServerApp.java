package ru.kpfu.itis;

import ru.kpfu.itis.sockets.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    public static void main(String[] args) {

        GameServer gameServer = GameServer.getInstance();
        gameServer.start();
        /*try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(in.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
