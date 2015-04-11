package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Screen;

/**
 * Created by miroshn on 09.04.15.
 * Перечисление возможных экранов
 */
public enum CustomScreen {
    MENU_SCREEN {
        @Override
        protected Screen getScreenInstance() {
            return new MenuScreen();
        }
    },

    LOAD_SCREEN {
        @Override
        protected com.badlogic.gdx.Screen getScreenInstance() {
            return new LoadScreen();
        }
    },

    WELCOME_SCREEN {
        @Override
        protected com.badlogic.gdx.Screen getScreenInstance() {
//            return new MenuScreen();
            return new WelcomeScreen();
        }
    },

    GAME {
        @Override
        protected com.badlogic.gdx.Screen getScreenInstance() {
            return new GameScreen();
        }
    },

    GAME_OVER {
        @Override
        protected com.badlogic.gdx.Screen getScreenInstance() {
            return new GameOverScreen();
        }
    };

//    BATTLE {
//        @Override
//        protected com.badlogic.gdx.Screen getScreenInstance() {
//            return new BattleScreen();
//        }
//    };

    protected abstract com.badlogic.gdx.Screen getScreenInstance();
}
