package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.EnemyIstrebitel;
import ru.miroshn.cartoon_raider.gameobjects.GameObject;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;
import ru.miroshn.cartoon_raider.helpers.InputHandler;
import ru.miroshn.cartoon_raider.helpers.ScreenInput;

import java.util.Random;

/**
 * Created by miroshn on 06.04.15.
 * класс отвечающий за основной этап игры
 */
public class GameScreen implements ScreenInput {
    private Stage stage;
    private Istrebitel player;
    private boolean clicked;
    private Array<GameObject> enemys;
    private Random rnd;
    private boolean debug = false;

    private ShapeRenderer shapeRenderer;

    public GameScreen() {
        shapeRenderer = new ShapeRenderer();
        enemys = new Array<GameObject>();
        player = new Istrebitel();
        player.setScale(0.5f);
        stage = new Stage();
        rnd = new Random();
        for (int i = 0; i < 10; i++) {
            enemys.add(new EnemyIstrebitel());
            enemys.get(i).setRotation(180);
            enemys.get(i).setScale(0.5f);
        }
//        stage.addActor(Background.getInstance());
//        stage.addActor(player);

        resetScreen();
    }

    @Override
    public void show() {
        stage.addActor(Background.getInstance());
        stage.addActor(player);
//        player.setDebug(true);

        for (GameObject g : enemys) {
            g.setPosition(rnd.nextInt(Gdx.graphics.getWidth()),
                    Gdx.graphics.getHeight() - g.getHeight() + rnd.nextInt(300));
            g.clearActions();
//            g.setDebug(true);
            g.addAction(Actions.moveTo(rnd.nextInt(Gdx.graphics.getWidth()), -200, (rnd.nextInt(100) + 50) / 10.f));
            stage.addActor(g);
        }
        resetScreen();
    }

    private void resetScreen() {
        clicked = false;
//        player.setPosition(Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight());
        player.setPosition(Gdx.graphics.getWidth() / 2, 0);

        MoveToAction action = new MoveToAction();
        action.setDuration(1);
        action.setPosition(Gdx.graphics.getWidth() / 2 - player.getWidth() / 2, 30);
        player.clearActions();
        player.setOrigin(player.getWidth() / 2, player.getHeight() / 2);
        player.addAction(action);

        Gdx.input.setInputProcessor(new InputHandler(this));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        for (GameObject g : enemys) {
            if (debug) {
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.rect(g.getBoundsRectangle().getX(), g.getBoundsRectangle().getY(), g.getBoundsRectangle().getWidth(), g.getBoundsRectangle().getHeight());
                shapeRenderer.rect(player.getBoundsRectangle().getX(), player.getBoundsRectangle().getY(), player.getBoundsRectangle().getWidth(), player.getBoundsRectangle().getHeight());
                shapeRenderer.polygon(g.getBoundingPolygon().getTransformedVertices());
                shapeRenderer.polygon(player.getBoundingPolygon().getTransformedVertices());
                shapeRenderer.end();
            }
            if (g.getBoundsRectangle().overlaps(player.getBoundsRectangle()))
                if (g.getBoundingPolygon().overlaps(player.getBoundingPolygon()))
                    ScreenManager.getInstance().show(CustomScreen.GAME_OVER);

        }

//        if (player.getY() > 300 && clicked) {
//            ScreenManager.getInstance().show(CustomScreen.GAME_OVER);
//        }
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
        player.dispose();
        stage.dispose();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 vec = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        vec.x -= player.getWidth() / 2;
        player.addAction(Actions.moveTo(vec.x, vec.y, 0.5f));
        return true;
    }

    @Override
    public boolean OnClick(int screenX, int screenY, int pointer, int button) {
        Vector2 vec = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        vec.x -= player.getWidth() / 2;
        player.addAction(Actions.moveTo(vec.x, vec.y, 0.5f));
//        Gdx.app.log("Click", "(" + vec.x + "," + vec.y + ")");
        clicked = true;
        return true;
    }
}
