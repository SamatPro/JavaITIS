package ru.kpfu.itis.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {

    private static final String POISON_PILL = "ПОКА";

    public static void main(String[] args) throws IOException {
        /**
         *  Создаем объект Selector, вызывая статический метод open.
         */
        Selector selector = Selector.open();
        /**
         * Создаем канал, также вызывая его статический открытый метод, экземпляр ServerSocketChannel.
         * Это потому что ServerSocketChannel выбирается и подходит для потокового прослушивающего сокета.
         */
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5454));
        //установить его в неблокирующий режим
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        /**
         * Java NIO использует модель, ориентированную на буфер, отличную от модели, ориентированной на потоки IO.
         * Обмен данными через сокеты обычно осуществляется посредством записи и чтения из буфера.
         * Создаем ByteBuffer, в который сервер будет записывать и читать.
         *  инициализируем его 256 байтами, это произвольное значение,
         *  зависящее от того, сколько данных мы планируем передавать туда и обратно.
         */
        ByteBuffer buffer = ByteBuffer.allocate(256);

        /**
         * Наконец, выполняем процесс выбора. Мы выбираем готовые каналы, получаем их selectedKeys,
         * перебираем ключи и выполняем операции, для которых каждый канал готов.
         *
         * Делаем в бесконечном цикле, поскольку серверам обычно необходимо
         * продолжать работу независимо от того, есть активность или нет.
         */
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {

                SelectionKey key = iter.next();

                /**
                 * Единственная операция, которую может обрабатывать ServerSocketChannel - это операция ACCEPT.
                 * Когда мы принимаем соединение от клиента, мы получаем объект SocketChannel,
                 * для которого можем выполнять чтение и запись.
                 */
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }

                /**
                 * Извлекаем его и считываем содержимое в буфер.
                 * Это эхо-сервер, поэтому записываем этот контент обратно клиенту.
                 */
                if (key.isReadable()) {
                    answerWithEcho(buffer, key);
                }
                iter.remove();
            }
        }
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key)
            throws IOException {

        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }
        else {
            /**
             * Выводим полученное сообщение в консоль.
             */
            //Здесь происходит интересное, почему? Исправить.
            System.out.println(new String(buffer.array()));
            /**
             * Когда мы хотим записать в буфер, из которого мы читали, мы должны вызвать метод buffer.flip()
             */
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        /**
         * Устанавливаем его в неблокирующий режим и регистрируем его для операции READ в селекторе.
         */
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }
}
