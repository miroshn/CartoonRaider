package ru.miroshn.cartoon_raider.gameobjects.ui;

import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * возможные виды заголовков используемых в программе <br>
 * Created by CAHEK on 11.04.2015.
 * @see Title
 */
public enum Titles {
    /**
     * Название игры
     */
    WELCOME_TITLE {
        @Override
        public Res getRes() {
            return Res.WELCOME_TITLE;
        }
    },
    /**
     * игра закончена
     */
    GAME_OVER_TITLE {
        @Override
        public Res getRes() {
            return Res.GAME_OVER_TITLE;
        }
    },
    /**
     * Пауза
     */
    GAME_PAUSED_TITLE {
        @Override
        public Res getRes() {
            return Res.GAME_PAUSED_TITLE;
        }
    };

    /**
     * Получение связанного ресурса
     *
     * @return ресурс связанный с конкретным значением перечисления
     * @see Res
     */
    public abstract Res getRes();
}
