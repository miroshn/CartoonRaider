package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.gameobjects.*;
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
    private Array<GameObject> enemys;
    private Random rnd;
    private MoveToAction moveToAction;


    private int score;

    private ShapeRenderer shapeRenderer;

    public GameScreen() {
        moveToAction = new MoveToAction();
        score = 0;
        shapeRenderer = new ShapeRenderer();
        enemys = new Array<GameObject>();
        player = new Istrebitel();
        stage = new Stage();
        rnd = new Random();
        for (int i = 0; i < 10; i++) {
            enemys.add(new EnemyIstrebitel());
            enemys.get(i).setRotation(180);
        }

        resetScreen();
    }

    @Override
    public void show() {
        stage.getActors().clear();
        stage.addActor(Background.getInstance());
        stage.addActor(player);
        stage.addActor(new Hud());
//        player.setDebug(true);

        player.init();
        for (GameObject g : enemys) {
            g.init();
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
        score = 0;
        player.setPosition(Gdx.graphics.getWidth() / 2, -player.getHeight());

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
        if (CartoonRaider.DEBUG) {
            for (Actor a : stage.getActors()) {
                if (a instanceof GameObject) {
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.polygon(((GameObject) a).getBoundingPolygon().getTransformedVertices());
                    shapeRenderer.end();
                }
            }
        }

        for (int j = 0; j < stage.getActors().size; j++) {
            Actor actor = stage.getActors().get(j);
            if (actor instanceof GameObject) {
                for (int i = j; i < stage.getActors().size; i++) {
                    Actor actor1 = stage.getActors().get(i);
                    if (actor1 instanceof GameObject) {
                        GameObject g1 = (GameObject) actor;
                        GameObject g2 = (GameObject) actor1;
                        if (g1 == g2) continue;
                        if (g1.getBoundingPolygon().overlaps(g2.getBoundingPolygon()) || g2.getBoundingPolygon().overlaps(g1.getBoundingPolygon())) {
                            g1.contact(g2);
                        }
                    }
                }
            }
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
        player.dispose();
        stage.dispose();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 vec = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        vec.x -= player.getWidth() / 2;
        player.getActions().removeValue(moveToAction, true);
        moveToAction.reset();
        moveToAction.setPosition(vec.x, vec.y);
        moveToAction.setDuration(0.5f);
        player.addAction(moveToAction);
        return true;
    }

    @Override
    public boolean OnClick(int screenX, int screenY, int pointer, int button) {
        Vector2 vec = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        vec.x -= player.getWidth() / 2;
        player.addAction(Actions.moveTo(vec.x, vec.y, 0.5f));
        return true;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
//        Gdx.app.log(this.toString(), "Score = " + this.score);
    }

    public int getPlayerHp() {
        return player.getHp();
    }
}
