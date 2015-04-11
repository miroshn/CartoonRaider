package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.GameOverTitle;
import ru.miroshn.cartoon_raider.helpers.InputHandler;
import ru.miroshn.cartoon_raider.helpers.ScreenInput;

/**
 * Created by miroshn on 07.04.15.
 * Экран - конец игры
 */
public class GameOverScreen implements ScreenInput {
    private Stage stage;
    private GameOverTitle gameOverTitle;

    public GameOverScreen() {
        stage = new Stage();
        gameOverTitle = new GameOverTitle();
//        stage.addActor(Background.getInstance());
    }

    @Override
    public void show() {
        gameOverTitle.clearActions();
        gameOverTitle.setScale(Gdx.graphics.getWidth() * 3.0f / 5.0f / gameOverTitle.getWidth());
        gameOverTitle.setPosition((Gdx.graphics.getWidth() - gameOverTitle.getWidth() * gameOverTitle.getScaleX()) / 2.0f,
                (Gdx.graphics.getHeight() - gameOverTitle.getHeight() * gameOverTitle.getScaleY()) / 2.0f);
        stage.addActor(Background.getInstance());
        stage.addActor(gameOverTitle);
        Gdx.input.setInputProcessor(new InputHandler(this));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

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
//        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void OnClick() {
        ScreenManager.getInstance().show(CustomScreen.WELCOME_SCREEN);
    }
}
