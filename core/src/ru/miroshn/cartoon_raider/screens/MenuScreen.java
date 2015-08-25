package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.ui.IstrebitelButton;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.ScreenInput;

/**
 * Created by CAHEK on 11.04.2015.
 * меню игры, должно реализовать запуск игры и выход
 */
public class MenuScreen implements ScreenInput {
    private final float BUTTON_SIZE = 1.0f / 5.0f;
    private final Stage stage;
    private final IstrebitelButton gameMenu, exitMenu, aboutMenu;
    private Table table;


    public MenuScreen() {
        gameMenu = new IstrebitelButton("Play");
        exitMenu = new IstrebitelButton("Exit");
        aboutMenu = new IstrebitelButton("About");

        table = new Table();
        table.setDebug(Conf.DEBUG);
        table.add(gameMenu, aboutMenu, exitMenu);
        float proportion = gameMenu.getHeight() / gameMenu.getWidth();
        for (Cell cell : table.getCells()) {
            cell.size(Gdx.graphics.getWidth() * BUTTON_SIZE, Gdx.graphics.getWidth() * BUTTON_SIZE * proportion);
            cell.pad(Gdx.graphics.getWidth() * BUTTON_SIZE / 4.0f);
        }
        table.setFillParent(true);

        stage = new Stage();

        exitMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        gameMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(CustomScreen.GAME);
            }
        });

        aboutMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(CustomScreen.WELCOME_SCREEN);
            }
        });
    }

    @Override
    public void show() {
        stage.addActor(Background.getInstance());
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {

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
    }

    @Override
    public boolean OnClick(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
}
