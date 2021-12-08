package ru.kpfu.itis.util;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GameUtils {

    private final int STEP = 5;

    public void goLeft(ImageView person) {
        person.setX(person.getX() - STEP);
    }

    public void goRight(ImageView person) {
        person.setX(person.getX() + STEP);
    }

    public static void shoot(ImageView shooter, ImageView target, AnchorPane pane, boolean shooterIsEnemy) {
        Circle bullet = new Circle();
        bullet.setRadius(5);
        // ставим пулю, там же, где и находится игрок в данный момент
        bullet.setCenterX(shooter.getX() + shooter.getLayoutX());
        bullet.setCenterY(shooter.getY() + shooter.getLayoutY());
        bullet.setFill(Color.ORANGE);
        // добавляю ее в сцену как вложенный объект относительно панели
        pane.getChildren().add(bullet);

        //Направление выстрела
        int value;
        // У игроков
        if (shooterIsEnemy) {
            value = 1;
        } else {
            value = -1;
        }

        // сделали анимацию выстрела
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), animation -> {
            bullet.setCenterY(bullet.getCenterY() + value);

            if (bullet.getCenterY() < 0 && bullet.getCenterY() > 500) {
                pane.getChildren().remove(bullet);
                System.out.println("Удалили объект");
            }

            if (bullet.isVisible() && isIntersects(bullet, target)) {
                bullet.setVisible(false);
            }
        }));

        timeline.setCycleCount(700);
        timeline.play();
    }

    private static boolean isIntersects(Circle bullet, ImageView target) {
        return bullet.getBoundsInParent().intersects(target.getBoundsInParent());
    }
}
