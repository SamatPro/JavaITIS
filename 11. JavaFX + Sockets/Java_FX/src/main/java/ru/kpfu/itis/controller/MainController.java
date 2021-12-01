package ru.kpfu.itis.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.kpfu.itis.enums.Action;
import ru.kpfu.itis.sockets.ClientSocket;
import ru.kpfu.itis.util.GameUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private GameUtils gameUtils;

    private ClientSocket clientSocket;

    private static final String PLAYER_ICON = "src/main/resources/img/img.png";

    @FXML
    private Button helloButton;

    @FXML
    private Label helloLabel;

    @FXML
    private ImageView player;

    @FXML
    private ImageView enemy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameUtils = new GameUtils();
        clientSocket = new ClientSocket();
        clientSocket.connect();
        try {
            Image playerIcon = new Image(new FileInputStream(PLAYER_ICON));

            player.setImage(playerIcon);
            player.setRotate(180);

            enemy.setImage(playerIcon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        helloButton.setOnAction(event -> {
            helloLabel.setText("Привет, игрок!");
        });
    }

    private final EventHandler<KeyEvent> playerControlEvent = event -> {
        switch (event.getCode()) {
            case RIGHT: {
                gameUtils.goRight(player);
                clientSocket.sendMessage(Action.RIGHT.getCode());
                System.out.println(Action.RIGHT.getDescription());
                break;
            }
            case LEFT: {
                gameUtils.goLeft(player);
                clientSocket.sendMessage(Action.LEFT.getCode());
                System.out.println(Action.LEFT.getDescription());
                break;
            }
            case SPACE: {
                gameUtils.shoot(player);
                clientSocket.sendMessage(Action.SHOOT.getCode());
                System.out.println(Action.SHOOT.getDescription());
            }
            case ESCAPE: {
                //TODO выход из игры
            }

        }
    };

    public EventHandler<KeyEvent> getPlayerControlEvent() {
        return playerControlEvent;
    }
}
