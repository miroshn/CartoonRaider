package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by miroshn on 06.04.15.
 */
public class WelcomeScreen implements Screen {
    private Game game;
    private Texture backgroundTexture;
    private Texture titleTexture;
    private SpriteBatch batch;
    private float scaleTitle, xTitle, yTitle;

    public WelcomeScreen(Game game) {
        this.game = game;
        backgroundTexture = new Texture("background.jpg");
        titleTexture = new Texture("title.png");
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scaleTitle = Gdx.graphics.getWidth() * 3.0f / 5.0f / (float) titleTexture.getWidth();
        xTitle = (Gdx.graphics.getWidth() - titleTexture.getWidth() * scaleTitle) / 2.0f;
        yTitle = (Gdx.graphics.getHeight() - titleTexture.getHeight() * scaleTitle) / 2.0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        for (int x = 0; x < Gdx.graphics.getWidth(); x += backgroundTexture.getWidth()) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y += backgroundTexture.getHeight()) {
                batch.draw(backgroundTexture, x, y);
            }
        }

        batch.draw(titleTexture, xTitle, yTitle, titleTexture.getWidth() * scaleTitle, titleTexture.getHeight() * scaleTitle);
        batch.end();
        if (!Gdx.input.isTouched()) {
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
        backgroundTexture.dispose();
        titleTexture.dispose();
        batch.dispose();
    }
}
