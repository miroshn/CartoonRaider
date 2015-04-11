package ru.miroshn.cartoon_raider.gameobjects;

/**
 * Created by CAHEK on 11.04.2015.
 * возможные виды заголовков
 */
public enum Titles {
    WELCOME_TITLE {
        @Override
        public String getFilename() {
            return "title.png";
        }
    },
    GAME_OVER_TITLE {
        @Override
        public String getFilename() {
            return "gameover.png";
        }
    };

    public abstract String getFilename();
}
