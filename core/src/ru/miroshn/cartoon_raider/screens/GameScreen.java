package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;
import ru.miroshn.cartoon_raider.helpers.InputHandler;
import ru.miroshn.cartoon_raider.helpers.ScreenInput;

/**
 * Created by miroshn on 06.04.15.
 * класс отвечающий за основной этап игры
 */
public class GameScreen implements ScreenInput {
    private Stage stage;
    private Istrebitel player;
    private boolean clicked;

    public GameScreen() {
        player = new Istrebitel();
        stage = new Stage();
//        stage.addActor(Background.getInstance());
//        stage.addActor(player);

        resetScreen();
    }

    @Override
    public void show() {
        stage.addActor(Background.getInstance());
        stage.addActor(player);
        resetScreen();
    }

    private void resetScreen() {
        clicked = false;
        player.setPosition(Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight());

        MoveToAction action = new MoveToAction();
        action.setDuration(1);
        action.setPosition(Gdx.graphics.getWidth() / 2 - player.getWidth() / 2, 30);
        player.clearActions();
        player.setOrigin(player.getWidth() / 2, player.getHeight() / 2);
        player.addAction(action);

        Gdx.input.setInputProcessor(new InputHandler(this));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if (player.getY() > 300 && clicked) {
            ScreenManager.getInstance().show(CustomScreen.GAME_OVER);
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
        player.dispose();
        stage.dispose();
    }

    @Override
    public boolean OnClick(int screenX, int screenY, int pointer, int button) {
        player.addAction(Actions.moveBy(0, 300, 2));
        clicked = true;
        return true;
    }
}
