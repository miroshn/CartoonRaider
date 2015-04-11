package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.InputHandler;
import ru.miroshn.cartoon_raider.helpers.ScreenInput;

/**
 * Created by miroshn on 06.04.15.
 * Показывается экран с названием..
 */
public class WelcomeScreen implements ScreenInput {
    private Texture titleTexture;
    private Title title;
    private Stage stage;
    private boolean cliked;

    public WelcomeScreen() {
        titleTexture = CRAssetManager.getInstance().get("title.png");
        title = new Title();
        stage = new Stage();
        resetScreen();
    }

    @Override
    public void show() {
        resetScreen();
    }

    private void resetScreen() {
        cliked = false;
        title.clearActions();
        title.setScale(Gdx.graphics.getWidth() * 3.0f / 5.0f / (float) titleTexture.getWidth());
        title.setPosition((Gdx.graphics.getWidth() - title.getWidth() * title.getScaleX()) / 2.0f,
                (Gdx.graphics.getHeight() - title.getHeight() * title.getScaleY()) / 2.0f);
        stage.addActor(Background.getInstance());
        stage.addActor(title);
        Gdx.input.setInputProcessor(new InputHandler(this));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if (cliked) {
            if (title.getY() < -300)
                ScreenManager.getInstance().show(CustomScreen.GAME);
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
        cliked = false;
    }

    @Override
    public void dispose() {
        titleTexture.dispose();
        stage.dispose();
    }

    @Override
    public void OnClick() {
        cliked = true;
    }


    class Title extends Actor {
        TextureRegion region;

        public Title() {
            setSize(titleTexture.getWidth(), titleTexture.getHeight());
            region = new TextureRegion(titleTexture);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(region, getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(), getRotation());
        }
    }
}
