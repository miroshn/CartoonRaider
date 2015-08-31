package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.ui.Hud;
import ru.miroshn.cartoon_raider.gameobjects.ui.Title;
import ru.miroshn.cartoon_raider.gameobjects.ui.Titles;

/**
 * Created by miroshn on 07.04.15.
 * Экран - конец игры
 */
public class GameOverScreen implements Screen {
    private final Stage stage;
    private final Title title;
    private final Hud hud;

    public GameOverScreen() {
        stage = new Stage();
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager.getInstance().show(CustomScreen.MENU_SCREEN);
                return true;
            }
        });
        title = new Title(Titles.GAME_OVER_TITLE);
        hud = new Hud();
    }

    @Override
    public void show() {
        title.clearActions();
        title.setScale(Gdx.graphics.getWidth() * 3.0f / 5.0f / title.getWidth());
        title.setPosition((Gdx.graphics.getWidth() - title.getWidth() * title.getScaleX()) / 2.0f,
                (Gdx.graphics.getHeight() - title.getHeight() * title.getScaleY()) / 2.0f);
        stage.addActor(Background.getInstance());
        stage.addActor(title);
        stage.addActor(hud);
        Gdx.input.setInputProcessor(stage);
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

}
