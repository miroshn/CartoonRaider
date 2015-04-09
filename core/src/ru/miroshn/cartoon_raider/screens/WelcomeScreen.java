package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.miroshn.cartoon_raider.gameobjects.Background;

/**
 * Created by miroshn on 06.04.15.
 * Показывается экран с названием..
 */
public class WelcomeScreen implements Screen {
    private Texture titleTexture;
    private Title title;
    private Stage stage;
    private Background background;

    public WelcomeScreen() {
        background = Background.getInstance();
        titleTexture = new Texture("title.png");

        title = new Title();

        title.setScale(Gdx.graphics.getWidth() * 3.0f / 5.0f / (float) titleTexture.getWidth());
        title.setPosition((Gdx.graphics.getWidth() - title.getWidth() * title.getScaleX()) / 2.0f,
                (Gdx.graphics.getHeight() - title.getHeight() * title.getScaleY()) / 2.0f);
        stage = new Stage();
        stage.addActor(background);
        stage.addActor(title);

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

        if (title.getY() < -300) ScreenManager.getInstance().show(CustomScreen.GAME);
        if (Gdx.input.isTouched()) {
            title.addAction(Actions.moveBy(0, -300, 2));
//            game.setScreen(new GameScreen());
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
        titleTexture.dispose();
        stage.dispose();
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
