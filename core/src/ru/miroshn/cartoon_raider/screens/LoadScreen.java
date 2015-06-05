package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by miroshn on 09.04.15.
 * Экран загрузки ресурсов
 */
public class LoadScreen implements Screen {
    private Texture progressBarImg, progressBarBaseImg;
    private SpriteBatch batch;
    private float percent;

    private void loadAssets() {
        if (Gdx.graphics.getDensity() > 1)
            CRAssetManager.getInstance().load(Res.FONT.getName(), BitmapFont.class);
        else
            CRAssetManager.getInstance().load(Res.FONT_16.getName(), BitmapFont.class);

        CRAssetManager.getInstance().load(Res.GRAPHIC_PACK.getName(), TextureAtlas.class);
        CRAssetManager.getInstance().load(Res.SHOT_SOUND.getName(), Sound.class);
        CRAssetManager.getInstance().load(Res.EXPLOSIVE_SOUND.getName(), Sound.class);
        CRAssetManager.getInstance().load(Res.ROCKET_SOUND.getName(), Sound.class);
        CRAssetManager.getInstance().load(Res.ALRAM_SOUND.getName(), Sound.class);
        percent = 0;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        CRAssetManager.getInstance().load(Res.PROGRESS_BAR.getName(), Texture.class);
        CRAssetManager.getInstance().load(Res.PROGRESS_BAR_BASE.getName(), Texture.class);
        CRAssetManager.getInstance().finishLoading();
        progressBarBaseImg = CRAssetManager.getInstance().get(Res.PROGRESS_BAR);
        progressBarImg = CRAssetManager.getInstance().get(Res.PROGRESS_BAR_BASE);
        loadAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        percent = Interpolation.linear.apply(percent, CRAssetManager.getInstance().getProgress(), 0.1f);

        batch.begin();
        batch.draw(progressBarBaseImg, 10, Gdx.graphics.getHeight() / 2 - progressBarBaseImg.getHeight() / 2,
                Gdx.graphics.getWidth() - 20, progressBarBaseImg.getHeight());
        batch.draw(progressBarImg, 10, Gdx.graphics.getHeight() / 2 - progressBarImg.getHeight() / 2,
                (Gdx.graphics.getWidth() - 20) * percent, progressBarImg.getHeight());
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
