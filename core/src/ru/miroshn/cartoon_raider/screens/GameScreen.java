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
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.EnemyIstrebitel;
import ru.miroshn.cartoon_raider.gameobjects.GameObject;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;
import ru.miroshn.cartoon_raider.gameobjects.ui.Hud;
import ru.miroshn.cartoon_raider.gameobjects.ui.Title;
import ru.miroshn.cartoon_raider.gameobjects.ui.Titles;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
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
    private boolean paused;
    private Hud hud;
    private int scrH, scrW;
    private Title pausedTitle;

    private ShapeRenderer shapeRenderer;

    public GameScreen() {
        pausedTitle = new Title(Titles.GAME_PAUSED_TITLE);
        scrW = Gdx.graphics.getWidth();
        scrH = Gdx.graphics.getHeight();
        hud = new Hud();
        moveToAction = new MoveToAction();
        CRAssetManager.getInstance().setScore(0);
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
        pausedTitle.setScale(scrW * 3.0f / 5.0f / pausedTitle.getWidth());
        pausedTitle.setPosition((scrW - pausedTitle.getWidth() * pausedTitle.getScaleX()) / 2.0f,
                (scrH - pausedTitle.getHeight() * pausedTitle.getScaleY()) / 2.0f);
        pausedTitle.setVisible(false);

        paused = false;
        stage.getActors().clear();
        stage.addActor(Background.getInstance());
        stage.addActor(player);
        stage.addActor(hud);
        stage.addActor(pausedTitle);
//        player.setDebug(true);

        player.init();
        for (GameObject g : enemys) {
            g.init();
            g.setPosition(rnd.nextInt(scrW), scrH - g.getHeight() + rnd.nextInt(300));
            g.clearActions();
//            g.setDebug(true);
            g.addAction(Actions.moveTo(rnd.nextInt(scrW), -200, (rnd.nextInt(100) + 50) / 10.f));
            stage.addActor(g);
        }
        resetScreen();
    }

    private void resetScreen() {
        CRAssetManager.getInstance().setScore(0);
        player.setPosition(scrW / 2, -player.getHeight());

        MoveToAction action = new MoveToAction();
        action.setDuration(1);
        action.setPosition(scrW / 2 - player.getWidth() / 2, 30);
        player.clearActions();
//        player.setOrigin(player.getWidth() / 2, player.getHeight() / 2);
        player.addAction(action);

        Gdx.input.setInputProcessor(new InputHandler(this));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!paused) {
            stage.act(delta);
        }
        stage.draw();
        if (CartoonRaider.DEBUG) {
            for (Actor a : stage.getActors()) {
                if (a instanceof GameObject) {
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.polygon(((GameObject) a).getBoundingPolygon(true).getTransformedVertices());
                    shapeRenderer.x(a.getX(), a.getY(), 5);
                    shapeRenderer.circle(a.getOriginX() + a.getX(), a.getOriginY() + a.getY(), 5);
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
                        if (g1.getBoundingPolygon(true).overlaps(g2.getBoundingPolygon(true)) || g2.getBoundingPolygon(true).overlaps(g1.getBoundingPolygon(true))) {
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
        if (hud.pauseTouched((int) vec.x, (int) vec.y)) {
            return true;
        }
        if (paused) return true;
        vec.x -= player.getWidth() * player.getScaleX() / 2;
        player.getActions().removeValue(moveToAction, true);
        moveToAction.reset();
        moveToAction.setPosition(vec.x, vec.y);
        moveToAction.setDuration(0.2f);
        player.addAction(moveToAction);
        return true;
    }

    @Override
    public boolean OnClick(int screenX, int screenY, int pointer, int button) {
        Vector2 vec = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        if (hud.pauseTouched((int) vec.x, (int) vec.y)) {
            paused = !paused;
            pausedTitle.setVisible(paused);
            return true;
        }

        if (paused) return true;
        vec.x -= player.getWidth() * player.getScaleX() / 2;
        player.getActions().removeValue(moveToAction, true);
        moveToAction.reset();
        moveToAction.setPosition(vec.x, vec.y);
        moveToAction.setDuration(0.5f);
        player.addAction(moveToAction);
        return true;
    }
}
