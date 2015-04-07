package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.gameobjects.GameWorld;

/**
 * Created by miroshn on 06.04.15.
 * класс отвечающий за основной этап игры
 */
public class GameScreen implements Screen {
    private Texture backgroundTexture;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Vector3 leftUpCorner, rightDownCorner;
    private SpriteBatch batch;
    private GameWorld world;
//    private CartoonRaider game;

    public GameScreen(CartoonRaider game) {
//        this.game = game;
        batch = new SpriteBatch();
        world = new GameWorld(batch, game);
        backgroundTexture = new Texture("background.jpg");
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        leftUpCorner = new Vector3(0, 0, 0);
        rightDownCorner = new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leftUpCorner.x = leftUpCorner.y = 0;
        rightDownCorner.x = Gdx.graphics.getWidth();
        rightDownCorner.y = Gdx.graphics.getHeight();

        camera.unproject(leftUpCorner);
        camera.unproject(rightDownCorner);

        batch.setProjectionMatrix(camera.combined);
//        camera.translate(0f, 1f);
        camera.update();

        batch.begin();

        int x = leftUpCorner.x < 0 ?
                (int) (leftUpCorner.x / backgroundTexture.getWidth() - 1) * backgroundTexture.getWidth() :
                (int) (leftUpCorner.x / backgroundTexture.getWidth()) * backgroundTexture.getWidth();
        for (; x < rightDownCorner.x; x += backgroundTexture.getWidth()) {
            int y = rightDownCorner.y < 0 ?
                    (int) (rightDownCorner.y / backgroundTexture.getHeight() - 1) * backgroundTexture.getHeight() :
                    (int) (rightDownCorner.y / backgroundTexture.getHeight()) * backgroundTexture.getHeight();
            for (; y < leftUpCorner.y; y += backgroundTexture.getHeight()) {
                batch.draw(backgroundTexture, x, y); // рисовать в мировых координатах!!!
            }
        }
        world.render(delta);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        backgroundTexture.dispose();
        batch.dispose();
        Gdx.app.log("DISPOSE", "GameOverScreen dispose");
    }
}
