package ru.kpfu.itis.enums;

public enum Action {

    RIGHT(0, "right", "Игрок передвинулся направо"),
    LEFT(1, "left", "Игрок передвинулся налево"),
    SHOOT(5, "shoot", "Игрок стреляет");

    private final int code;
    private final String title;
    private final String description;

    Action(int code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
