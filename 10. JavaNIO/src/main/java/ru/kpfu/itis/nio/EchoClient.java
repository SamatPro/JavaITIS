package ru.kpfu.itis.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class EchoClient {
    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static EchoClient instance;

    public static EchoClient start() {
        // Что за паттерн используется?
        if (instance == null)
            instance = new EchoClient();

        return instance;
    }

    public static void stop() throws IOException {
        client.close();
        buffer = null;
    }

    private EchoClient() {

        try {
            /**
             * открываем соединение на том же порту, к которому был привязан канал сервера и на том же хосте.
             */
            client = SocketChannel.open(new InetSocketAddress("localhost", 5454));
            /**
             * создаем буфер, в который мы можем писать и из которого можем читать.
             */
            buffer = ByteBuffer.allocate(256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод sendMessage считывает строку и передает в байтовый буфер,
     * который передается по каналу на сервер.
     */
    public String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        String response = null;
        try {
            client.write(buffer);
            buffer.clear();
            /**
             * читаем из клиентского канала, чтобы получить сообщение,
             * отправленное сервером. мы возвращаем это как эхо нашего сообщения.
             */
            client.read(buffer);
            response = new String(buffer.array()).trim();
            System.out.println("response=" + response);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

    public static void main(String[] args) {
        EchoClient client = EchoClient.start();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String message = sc.nextLine();
            String response = client.sendMessage(message);
            System.out.println(response);
        }
    }
}
