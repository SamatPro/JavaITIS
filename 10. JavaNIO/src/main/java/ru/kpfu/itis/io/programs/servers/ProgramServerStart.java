package ru.kpfu.itis.io.programs.servers;

import ru.kpfu.itis.io.servers.GreetServer;

public class ProgramServerStart {
    public static void main(String[] args) {
        // создаем экземпляр сервера
        GreetServer server = new GreetServer();
        // размещаем сокет на порту 6666
        server.start(6666);
    }
}
