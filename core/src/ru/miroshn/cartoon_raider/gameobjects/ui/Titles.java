package ru.miroshn.cartoon_raider.gameobjects.ui;

/**
 * Created by CAHEK on 11.04.2015.
 * возможные виды заголовков
 */
public enum Titles {
    WELCOME_TITLE {
        @Override
        public String getFilename() {
            return "title";
        }
    },
    GAME_OVER_TITLE {
        @Override
        public String getFilename() {
            return "gameover";
        }
    },
    GAME_PAUSED_TITLE {
        @Override
        public String getFilename() {
            return "pause_title";
        }
    };

    public abstract String getFilename();
}
