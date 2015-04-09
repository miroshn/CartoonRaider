package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.miroshn.cartoon_raider.CRAssetManager;

/**
 * Created by miroshn on 09.04.15.
 * Экран загрузки ресурсов
 */
public class LoadScreen implements Screen {
    private Texture progressBarImg, progressBarBaseImg;
    private SpriteBatch batch;

    private void loadAssets() {
        CRAssetManager.getInstance().load("background.jpg", Texture.class);
        CRAssetManager.getInstance().load("title.png", Texture.class);
        CRAssetManager.getInstance().load("gameover.png", Texture.class);
        CRAssetManager.getInstance().load("istrebitel1.png", Texture.class);
    }
    
    @Override
    public void show() {
        batch = new SpriteBatch();
        CRAssetManager.getInstance().load("progress_bar.png", Texture.class);
        CRAssetManager.getInstance().load("progress_bar_base.png", Texture.class);
        CRAssetManager.getInstance().finishLoading();
        progressBarBaseImg = CRAssetManager.getInstance().get("progress_bar_base.png");
        progressBarImg = CRAssetManager.getInstance().get("progress_bar.png");
        loadAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(progressBarBaseImg, 0, 0);
        batch.draw(progressBarImg, 0, 0,
                progressBarImg.getWidth() * CRAssetManager.getInstance().getProgress(), progressBarImg.getHeight());
        batch.end();

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
        batch.dispose();
    }
}
