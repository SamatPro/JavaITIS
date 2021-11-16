package ru.kpfu.itis.io.programs.servers;

import ru.kpfu.itis.io.servers.ChatMultiServer;

public class ProgramChatMultiServer {
    public static void main(String[] args) {
        ChatMultiServer server = new ChatMultiServer();
        server.start(6666);
    }
}
