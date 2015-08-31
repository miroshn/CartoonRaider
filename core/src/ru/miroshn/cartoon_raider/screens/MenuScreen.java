package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.ui.IstrebitelButton;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.ExitDialog;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by CAHEK on 11.04.2015.
 * меню игры, должно реализовать запуск игры и выход
 */
public class MenuScreen implements Screen {
    private final float BUTTON_SIZE = 1.0f / 6.0f;
    private final Stage stage;
    private final IstrebitelButton gameMenu, exitMenu, aboutMenu, optionsMenu;
    private Table table;
    private ExitDialog exitDialog;


    public MenuScreen() {
        gameMenu = new IstrebitelButton("Play");
        exitMenu = new IstrebitelButton("Exit");
        aboutMenu = new IstrebitelButton("About");
        optionsMenu = new IstrebitelButton("Settings");

        Window.WindowStyle windowStyle = new Window.WindowStyle(((BitmapFont) CRAssetManager.getInstance().get(Res.FONT))
                , Color.BLACK, new TextureRegionDrawable(((TextureRegion) CRAssetManager.getInstance().get(Res.EXIT_DIALOG))));
        exitDialog = new ExitDialog("", windowStyle);


        table = new Table();
        table.setDebug(Conf.DEBUG);
        table.add(gameMenu, aboutMenu, optionsMenu, exitMenu);
        float proportion = gameMenu.getHeight() / gameMenu.getWidth();
        for (Cell cell : table.getCells()) {
            cell.size(Gdx.graphics.getWidth() * BUTTON_SIZE, Gdx.graphics.getWidth() * BUTTON_SIZE * proportion);
            cell.pad(Gdx.graphics.getWidth() * BUTTON_SIZE / 4.0f);
        }
        table.setFillParent(true);

        stage = new Stage();
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                    exitDialogShowHide();
                }
                return true;
            }
        });

        exitMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitDialogShowHide();
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

        optionsMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(CustomScreen.OPTIONS_SCREEN);
            }
        });
    }

    private void exitDialogShowHide() {
        if (exitDialog.getStage() != null) {
            exitDialog.hide();
        } else {
            exitDialog.show(stage);
        }
    }

    @Override
    public void show() {
        stage.getActors().clear();
        stage.addActor(Background.getInstance());
        stage.addActor(table);
        Gdx.input.setCatchBackKey(true);
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
        Gdx.app.debug(getClass().getSimpleName(), "dispose called");
        stage.dispose();
    }

}
