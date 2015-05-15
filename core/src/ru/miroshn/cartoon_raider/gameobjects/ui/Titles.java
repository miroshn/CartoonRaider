package ru.miroshn.cartoon_raider.gameobjects.ui;

import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by CAHEK on 11.04.2015.
 * возможные виды заголовков
 */
public enum Titles {
    WELCOME_TITLE {
        @Override
        public Res getRes() {
            return Res.WELCOME_TITLE;
        }
    },
    GAME_OVER_TITLE {
        @Override
        public Res getRes() {
            return Res.GAME_OVER_TITLE;
        }
    },
    GAME_PAUSED_TITLE {
        @Override
        public Res getRes() {
            return Res.GAME_PAUSED_TITLE;
        }
    };

    public abstract Res getRes();
}
