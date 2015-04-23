package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

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
        CRAssetManager.getInstance().load("explosive1.png", Texture.class);
        CRAssetManager.getInstance().load("explosive2.png", Texture.class);
        CRAssetManager.getInstance().load("explosive3.png", Texture.class);
        CRAssetManager.getInstance().load("bullet.png", Texture.class);
        CRAssetManager.getInstance().load("stars.png", Texture.class);
        CRAssetManager.getInstance().load("border_bar.png", Texture.class);
        CRAssetManager.getInstance().load("bar.png", Texture.class);
        CRAssetManager.getInstance().load("hp.png", Texture.class);
        CRAssetManager.getInstance().load("rof.png", Texture.class);
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
        batch.draw(progressBarBaseImg, 10, Gdx.graphics.getHeight() / 2 - progressBarBaseImg.getHeight() / 2,
                Gdx.graphics.getWidth() - 20, progressBarBaseImg.getHeight());
        batch.draw(progressBarImg, 10, Gdx.graphics.getHeight() / 2 - progressBarImg.getHeight() / 2,
                (Gdx.graphics.getWidth() - 20) * CRAssetManager.getInstance().getProgress(), progressBarImg.getHeight());
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
