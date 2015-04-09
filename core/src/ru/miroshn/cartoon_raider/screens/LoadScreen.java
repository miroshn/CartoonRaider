package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import ru.miroshn.cartoon_raider.CRAssetManager;

/**
 * Created by miroshn on 09.04.15.
 * Экран загрузки ресурсов
 */
public class LoadScreen implements Screen {

    private void loadAssets() {
        CRAssetManager.getInstance().load("background.jpg", Texture.class);
        CRAssetManager.getInstance().load("title.png", Texture.class);
        CRAssetManager.getInstance().load("gameover.png", Texture.class);
        CRAssetManager.getInstance().load("istrebitel1.png", Texture.class);
    }
    
    @Override
    public void show() {
        CRAssetManager.getInstance().init();
        loadAssets();
    }

    @Override
    public void render(float delta) {
        if (CRAssetManager.getInstance().update()) {
            ScreenManager.getInstance().show(CustomScreen.WELCOME_SCREEN);
        }
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
