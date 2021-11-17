package ru.kpfu.itis.filetransfer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Absalom Herrera
 */
public class FileReceive {

    public static void main(String[] args) {
        System.out.println("Ready to receive!");
        int port = 1234;
        String serverRoute = "C:\\Users\\Samat\\Desktop\\storage"; // где будет храниться файл
        createFile(port, serverRoute); // создание файла
    }

    public static void createFile (int port, String serverRoute){
        try{
            DatagramSocket socket = new DatagramSocket(port);
            byte[] receiveFileName = new byte[1024]; // Храним данные о названии файла датаграм
            DatagramPacket receiveFileNamePacket = new DatagramPacket(receiveFileName, receiveFileName.length);
            socket.receive(receiveFileNamePacket); // Получение датаграммы с названием файла
            System.out.println("Receiving file name");
            byte [] data = receiveFileNamePacket.getData(); // Чтение названия файла в байты
            String fileName = new String(data, 0, receiveFileNamePacket.getLength()); // конвертация названия в строку

            System.out.println("Creating file");
            File f = new File (serverRoute + "\\" + fileName); // создание файла
            FileOutputStream outToFile = new FileOutputStream(f); // создание потока данных через которую передаем контент

            receiveFile(outToFile, socket); // получение файла
        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static void receiveFile(FileOutputStream outToFile, DatagramSocket socket) throws IOException {
        System.out.println("Receiving file");
        boolean flag; // окончание файла
        int sequenceNumber = 0; // порядок последовательности
        int foundLast = 0; // найдена последняя последовательность

        while (true) {
            byte[] message = new byte[1024]; // хранение полученной датаграммы
            byte[] fileByteArray = new byte[1021]; // где храним данные, которые запишутся в файл

            // получение пакета
            DatagramPacket receivedPacket = new DatagramPacket(message, message.length);
            socket.receive(receivedPacket);
            message = receivedPacket.getData(); // данные записываемые в файл

            // получаем порт и адрес отправителя
            InetAddress address = receivedPacket.getAddress();
            int port = receivedPacket.getPort();

            // получение порядкового номера
            sequenceNumber = ((message[0] & 0xff) << 8) + (message[1] & 0xff);
            // Проверяем, дошли ли мы до последней датаграммы (конец файла)
            flag = (message[2] & 0xff) == 1;

            // Получаем данные из сообщения и пишем подтверждение, что оно получено правильно
            if (sequenceNumber == (foundLast + 1)) {

                // установка последнего порядкового номера
                foundLast = sequenceNumber;

                // получение данных из сообщения
                System.arraycopy(message, 3, fileByteArray, 0, 1021);

                // Записать полученные данные в файл и распечатать порядковый номер полученных данных
                outToFile.write(fileByteArray);
                System.out.println("Received: Sequence number:" + foundLast);

                // Отправить подтверждение
                sendAck(foundLast, socket, address, port);
            } else {
                System.out.println("Expected sequence number: " + (foundLast + 1) + " but received " + sequenceNumber + ". DISCARDING");
                // Повторно отправить подтверждение
                sendAck(foundLast, socket, address, port);
            }
            //Проверить последнюю дейтаграмму
            if (flag) {
                outToFile.close();
                break;
            }
        }
    }

    private static void sendAck(int foundLast, DatagramSocket socket, InetAddress address, int port) throws IOException {
        // отправить подтверждение
        byte[] ackPacket = new byte[2];
        ackPacket[0] = (byte) (foundLast >> 8);
        ackPacket[1] = (byte) (foundLast);
        // пакет дейтаграммы для отправки
        DatagramPacket acknowledgement = new DatagramPacket(ackPacket, ackPacket.length, address, port);
        socket.send(acknowledgement);
        System.out.println("Sent ack: Sequence Number = " + foundLast);
    }
}