package ru.kpfu.itis.filetransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class FileSend {
    /*
        Метод для получения файла для отправки и передачи его названию
     */
    private void ready(int port, String host) {

        System.out.println("Выбираем файл");
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(host);
            String fileName;

            Scanner scanner = new Scanner(System.in);

            File f = new File(scanner.nextLine());
            fileName = f.getName();
            byte[] fileNameBytes = fileName.getBytes(); // Имя файла в байтах для его отправки
            DatagramPacket fileStatPacket = new DatagramPacket(fileNameBytes, fileNameBytes.length, address, port); //Пакет имени файла
            socket.send(fileStatPacket); // Отправка пакета с именем файла

            byte[] fileByteArray = readFileToByteArray(f); // Массив байтов, из которых состоит файл
            sendFile(socket, fileByteArray, address, port); // Ввод метода отправки фактического файла

            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void sendFile(DatagramSocket socket, byte[] fileByteArray, InetAddress address, int port) throws IOException {
        System.out.println("Sending file");
        int sequenceNumber = 0; // Для очереди
        boolean flag; // Чтобы узнать, дошли ли мы до конца файла
        int ackSequence = 0; // Чтобы узнать, правильно ли была получена дейтаграмма

        for (int i = 0; i < fileByteArray.length; i = i + 1021) {
            sequenceNumber += 1;

            // Создать сообщение
            byte[] message = new byte[1024]; // Первые два байта данных предназначены для управления (целостность и порядок дейтаграммы)
            message[0] = (byte) (sequenceNumber >> 8);
            message[1] = (byte) (sequenceNumber);

            if ((i + 1021) >= fileByteArray.length) { // Мы дошли до конца файла?
                flag = true;
                message[2] = (byte) (1); // Мы достигли конца файла (последняя отправленная датаграмма)
            } else {
                flag = false;
                message[2] = (byte) (0); // Мы не достигли конца файла, датаграммы все еще отправляются
            }

            if (!flag) {
                System.arraycopy(fileByteArray, i, message, 3, 1021);
            } else { //Если это последняя дейтаграмма
                System.arraycopy(fileByteArray, i, message, 3, fileByteArray.length - i);
            }

            DatagramPacket sendPacket = new DatagramPacket(message, message.length, address, port); //Данные для отправки
            socket.send(sendPacket); // Отправка данных
            System.out.println("Sent: Sequence number = " + sequenceNumber);

            boolean ackRec; // Была ли получена дейтаграмма?

            while (true) {
                byte[] ack = new byte[2]; // Создать еще один пакет или подтверждение дейтаграммы
                DatagramPacket ackpack = new DatagramPacket(ack, ack.length);

                try {
                    socket.setSoTimeout(50); // Ожидание отправки подтверждения от сервера
                    socket.receive(ackpack);
                    ackSequence = ((ack[0] & 0xff) << 8) + (ack[1] & 0xff); // Определение порядкового номера
                    ackRec = true; // Мы получили подтверждение
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket timed out waiting for ack");
                    ackRec = false; // Мы не получили подтверждение
                }

                //Если пакет был получен правильно, следующий пакет может быть отправлен
                if ((ackSequence == sequenceNumber) && (ackRec)) {
                    System.out.println("Ack received: Sequence Number = " + ackSequence);
                    break;
                } // Пакет не был получен, поэтому мы отправляем его повторно
                else {
                    socket.send(sendPacket);
                    System.out.println("Resending: Sequence Number = " + sequenceNumber);
                }
            }
        }
    }

    private static byte[] readFileToByteArray(File file) {
        FileInputStream fis = null;
        // Создание байтового массива с использованием длины файла
        // file.length возвращает long, которое приводится к int
        byte[] bArray = new byte[(int) file.length()];
        try {
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }
        return bArray;
    }

    public static void main(String[] args) {
        int port = 1234;
        String host = "127.0.0.1";
        FileSend fs = new FileSend();
        fs.ready(port, host);
    }
}
