package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.IstrebitelButton;
import ru.miroshn.cartoon_raider.helpers.ScreenInput;

/**
 * Created by CAHEK on 11.04.2015.
 * меню игры, должно реализовать запуск игры и выход
 */
public class MenuScreen implements ScreenInput {
    private Stage stage;
    private BitmapFont font;
    private IstrebitelButton gameMenu, exitMenu;


    public MenuScreen() {
        gameMenu = new IstrebitelButton("Play");
        exitMenu = new IstrebitelButton("Exit");
        stage = new Stage();
        font = new BitmapFont();
    }

    @Override
    public void OnClick() {

    }

    @Override
    public void show() {
        gameMenu.setPosition(100, 500);
        exitMenu.setPosition(100, 100);

        stage.addActor(Background.getInstance());
        stage.addActor(gameMenu);
        stage.addActor(exitMenu);
//        Gdx.input.setInputProcessor(new InputHandler(this));
    }

    @Override
    public void render(float delta) {

        stage.act();
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
        font.dispose();
    }
}
