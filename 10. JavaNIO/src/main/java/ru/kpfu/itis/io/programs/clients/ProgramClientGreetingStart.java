package ru.kpfu.itis.io.programs.clients;

import ru.kpfu.itis.io.clients.SocketClient;

public class ProgramClientGreetingStart {
    public static void main(String[] args) {
        // создали объект клиента
        SocketClient client = new SocketClient();
        // подключились к серверу
        client.startConnection("127.0.0.1", 6666);
        // отправляете сообщение и получаете ответ
        client.sendMessage("hello server");
    }
}