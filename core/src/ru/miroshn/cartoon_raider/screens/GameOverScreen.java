package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.miroshn.cartoon_raider.CartoonRaider;

/**
 * Created by miroshn on 07.04.15.
 * Экран - конец игры
 */
public class GameOverScreen implements Screen {
    private CartoonRaider game;
    private SpriteBatch batch;
    private Texture gameOverTexture;
    private float scaleGO, xGO, yGO;

    public GameOverScreen(CartoonRaider game) {
        this.game = game;
        batch = new SpriteBatch();
        gameOverTexture = new Texture("gameover.png");
        scaleGO = Gdx.graphics.getWidth() * 3.0f / 5.0f / (float) gameOverTexture.getWidth();
        xGO = (Gdx.graphics.getWidth() - gameOverTexture.getWidth() * scaleGO) / 2.0f;
        yGO = (Gdx.graphics.getHeight() - gameOverTexture.getHeight() * scaleGO) / 2.0f;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(gameOverTexture, xGO, yGO, gameOverTexture.getWidth() * scaleGO, gameOverTexture.getHeight() * scaleGO);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen());
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
        gameOverTexture.dispose();
        Gdx.app.log("DISPOSE", "GameOverScreen dispose");
    }
}
