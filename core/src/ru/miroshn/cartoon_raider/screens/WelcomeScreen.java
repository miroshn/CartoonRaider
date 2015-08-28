package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.ui.Title;
import ru.miroshn.cartoon_raider.gameobjects.ui.Titles;

/**
 * Created by miroshn on 06.04.15.
 * Показывается экран с названием..
 */
public class WelcomeScreen implements Screen {
    private final Title title;
    private final Stage stage;
    private boolean cliked;

    public WelcomeScreen() {
        title = new Title(Titles.WELCOME_TITLE);
        stage = new Stage();
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cliked = true;
            }
        });

        resetScreen();
    }

    @Override
    public void show() {
        resetScreen();
    }

    private void resetScreen() {
        cliked = false;
        title.clearActions();
        title.setScale(Gdx.graphics.getWidth() * 3.0f / 5.0f / title.getWidth());
        title.setPosition((Gdx.graphics.getWidth() - title.getWidth() * title.getScaleX()) / 2.0f,
                (Gdx.graphics.getHeight() - title.getHeight() * title.getScaleY()) / 2.0f);
        stage.addActor(Background.getInstance());
        stage.addActor(title);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if (cliked) {
            if (title.getY() < -300)
                ScreenManager.getInstance().show(CustomScreen.MENU_SCREEN);
            title.addAction(Actions.moveBy(0, -300, 2));
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
//        stage.dispose();
        Gdx.input.setInputProcessor(null);
        cliked = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
