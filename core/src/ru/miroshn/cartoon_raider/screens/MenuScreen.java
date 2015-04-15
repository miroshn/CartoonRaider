package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.miroshn.cartoon_raider.CartoonRaider;
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
    private IstrebitelButton gameMenu, exitMenu, aboutMenu;


    public MenuScreen() {
        gameMenu = new IstrebitelButton("Play");
        exitMenu = new IstrebitelButton("Exit");
        aboutMenu = new IstrebitelButton("About");
        stage = new Stage();
        font = new BitmapFont();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float yCoord = height / 2 - gameMenu.getWidth() / 2;
        gameMenu.setPosition(width * 1 / 5 - gameMenu.getWidth() / 2, yCoord);
        exitMenu.setPosition(width * 4 / 5 - exitMenu.getWidth() / 2, yCoord);
        aboutMenu.setPosition(width * 2.5f / 5 - aboutMenu.getWidth() / 2, yCoord);

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
        gameMenu.setScale(CartoonRaider.SCALE * 2);
        exitMenu.setScale(CartoonRaider.SCALE * 2);
        aboutMenu.setScale(CartoonRaider.SCALE * 2);

    }

    @Override
    public void show() {
        stage.addActor(Background.getInstance());
        stage.addActor(gameMenu);
        stage.addActor(exitMenu);
        stage.addActor(aboutMenu);
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

    @Override
    public boolean OnClick(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
}
