package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;

/**
 * Created by miroshn on 06.04.15.
 * класс отвечающий за основной этап игры
 */
public class GameScreen implements Screen {
    private Stage stage;
    private Istrebitel player;
    private Background background;

    public GameScreen() {
        player = new Istrebitel();
        player.setPosition(Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight());

        MoveToAction action = new MoveToAction();
        action.setDuration(1);
        action.setPosition(Gdx.graphics.getWidth() / 2 - player.getWidth() / 2, 30);
        player.setOrigin(player.getWidth() / 2, player.getHeight() / 2);
        player.addAction(action);

        background = Background.getInstance();

        stage = new Stage();
        stage.addActor(background);
        stage.addActor(player);


    }

    @Override
    public void show() {

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

    }

    @Override
    public void dispose() {
        player.dispose();
        stage.dispose();
    }
}
