package ru.kpfu.itis.util;


import javafx.scene.image.ImageView;
import ru.kpfu.itis.enums.Action;

public class GameUtils {

    private final int STEP = 5;

    public void goLeft(ImageView person) {
        person.setX(person.getX() - STEP);
    }

    public void goRight(ImageView person) {
        person.setX(person.getX() + STEP);
    }

    public void shoot(ImageView person) {
        //TODO
    }
}
