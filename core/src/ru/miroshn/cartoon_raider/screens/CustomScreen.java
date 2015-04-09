package ru.miroshn.cartoon_raider.screens;

/**
 * Created by miroshn on 09.04.15.
 * Перечисление возможных экранов
 */
public enum CustomScreen {
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
    };

//    BATTLE {
//        @Override
//        protected com.badlogic.gdx.Screen getScreenInstance() {
//            return new BattleScreen();
//        }
//    };

    protected abstract com.badlogic.gdx.Screen getScreenInstance();
}
