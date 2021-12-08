package ru.kpfu.itis.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.enums.Action;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageType;
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
    private Button connectButton;

    @FXML
    private AnchorPane gameArea;

    @FXML
    private TextField name;

    @FXML
    private Label helloLabel;

    @FXML
    private ImageView player;

    @FXML
    private ImageView enemy;

    @FXML
    private ScrollPane messagesArea;

    @FXML
    private VBox messages;

    @FXML
    private VBox messageControl;

    @FXML
    private TextField messageText;

    @FXML
    private Button sendMessageButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameUtils = new GameUtils();
        try {
            Image playerIcon = new Image(new FileInputStream(PLAYER_ICON));

            player.setImage(playerIcon);
            player.setRotate(180);

            enemy.setImage(playerIcon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sendMessageButton.setOnMouseClicked(event -> {
            sendMessage();
        });
        connectButton.setOnMouseClicked(event -> {
            String nickname = name.getText();
            helloLabel.setText("Привет, " + nickname);
            connectButton.setDisable(true);
            name.setEditable(false);
            clientSocket = new ClientSocket();
            clientSocket.connect(this, nickname);

        });
    }

    private final EventHandler<KeyEvent> playerControlEvent = event -> {
        switch (event.getCode()) {
            case RIGHT: {
                gameUtils.goRight(player);
                Message message = new Message();
                message.setType(MessageType.ACTION);
                message.setBody(Action.RIGHT.getTitle());
                clientSocket.sendMessage(message);

                System.out.println(Action.RIGHT.getDescription());
                break;
            }
            case LEFT: {
                gameUtils.goLeft(player);
                Message message = new Message();
                message.setType(MessageType.ACTION);
                message.setBody(Action.LEFT.getTitle());
                clientSocket.sendMessage(message);

                System.out.println(Action.LEFT.getDescription());
                break;
            }
            case ALT_GRAPH: {
                GameUtils.shoot(player, enemy, gameArea, false);
                Message message = new Message();
                message.setType(MessageType.ACTION);
                message.setBody(Action.SHOOT.getTitle());
                clientSocket.sendMessage(message);
                System.out.println(Action.SHOOT.getDescription());
                break;

               /* gameUtils.shoot(player);
                Message message = new Message();
                message.setType(MessageType.ACTION);
                message.setBody(Action.SHOOT.getTitle());
                clientSocket.sendMessage(message);*/

//                System.out.println(Action.SHOOT.getDescription());
//                break;
            }
            case ENTER: {
                sendMessage();
                break;
            }
            case ESCAPE: {
                //TODO выход из игры
            }

        }
    };

    public EventHandler<KeyEvent> getPlayerControlEvent() {
        return playerControlEvent;
    }

    private void sendMessage() {
        String chatText = messageText.getText();
        messageText.clear();
        Message message = new Message();
        message.setType(MessageType.CHAT);
        message.setBody(chatText);
        clientSocket.sendMessage(message);
        Label label = new Label();
        //label.setStyle("-fx-font: Corier new");
        label.setText("Я: " + chatText);

        messages.getChildren().add(label);
    }

    public VBox getMessages() {
        return messages;
    }
}
