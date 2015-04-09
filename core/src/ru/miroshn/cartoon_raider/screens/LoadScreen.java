package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Screen;

/**
 * Created by miroshn on 09.04.15.
 * Экран загрузки ресурсов
 */
public class LoadScreen implements Screen {
    @Override
    public void show() {
        ScreenManager.getInstance().show(CustomScreen.WELCOME_SCREEN);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
