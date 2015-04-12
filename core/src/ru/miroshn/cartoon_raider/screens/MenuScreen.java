package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.IstrebitelButton;

/**
 * Created by CAHEK on 11.04.2015.
 * меню игры, должно реализовать запуск игры и выход
 */
public class MenuScreen implements Screen {
    private Stage stage;
    private BitmapFont font;
    private IstrebitelButton gameMenu, exitMenu;


    public MenuScreen() {
        gameMenu = new IstrebitelButton("Play");
        exitMenu = new IstrebitelButton("Exit");
        stage = new Stage();
        font = new BitmapFont();
    }

//    @Override
//    public boolean OnClick(int screenX, int screenY, int pointer, int button) {
//        Gdx.app.log("MenuScreen","OnClick");
//        return true;
//    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float yCoord = height / 2 - gameMenu.getWidth() / 2;
        gameMenu.setPosition(width * 1 / 5 - gameMenu.getWidth() / 2, yCoord);
        exitMenu.setPosition(width * 4 / 5 - exitMenu.getWidth() / 2, yCoord);

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

        stage.addActor(Background.getInstance());
        stage.addActor(gameMenu);
        stage.addActor(exitMenu);
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
        font.dispose();
    }
}
