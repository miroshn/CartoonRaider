package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.gameobjects.*;
import ru.miroshn.cartoon_raider.gameobjects.ui.Hud;
import ru.miroshn.cartoon_raider.gameobjects.ui.Title;
import ru.miroshn.cartoon_raider.gameobjects.ui.Titles;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.ExitDialog;
import ru.miroshn.cartoon_raider.helpers.Res;

import java.util.Random;

/**
 * Created by miroshn on 06.04.15.
 * класс отвечающий за основной этап игры
 */
public class GameScreen implements Screen {
    private final Stage stage;
    private final Istrebitel player;
    private final Array<GameObject> enemys;
    private final Random rnd;
    private final MoveToAction moveToAction;
    private final Hud hud;
    private final int scrH;
    private final int scrW;
    private final Title pausedTitle;
    private final ShapeRenderer shapeRenderer;
    private boolean paused;
    private Boss1 boss1;
    private Boss1 boss2;
    private GameStages gameGtage = GameStages.BEGIN;
    private Sound alramSound;
    private float dX, dY;
    private ExitDialog exitDialog;

    public GameScreen() {
        dX = dY = -1;
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
        stage.addListener(new InputListener() {
//            private ExitDialog exitDialog;

            {
                Window.WindowStyle windowStyle = new Window.WindowStyle(((BitmapFont) CRAssetManager.getInstance().get(Res.FONT))
                        , Color.BLACK, new TextureRegionDrawable(((TextureRegion) CRAssetManager.getInstance().get(Res.EXIT_DIALOG))));
                exitDialog = new ExitDialog("", windowStyle, CustomScreen.MENU_SCREEN);
                exitDialog.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        paused = false;
                    }
                });
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                    if (exitDialog.getStage() != null) {
                        exitDialog.hide();
                        paused = false;
                    } else {
                        exitDialog.show(stage);
                        paused = true;
                    }
                }
                return true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                Vector2 vec = stage.screenToStageCoordinates(new Vector2(x, y));
//                Gdx.app.log(getClass().getSimpleName(),"exit Dialog visible " + exitDialog.isVisible());
                if (hud.pauseTouched((int) x, (int) y) && !exitDialog.isVisible()) {
                    paused = !paused;
                    pausedTitle.setVisible(paused);
                    return true;
                }


                Vector2 dVec = new Vector2(player.getX(), player.getY());
                dX = (x - dVec.x - player.getWidth() * player.getScaleX() / 2);
                dY = y - (int) dVec.y;
                return true;

            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector2 vec = new Vector2(x, y);
                if (hud.pauseTouched((int) vec.x, (int) vec.y) && !exitDialog.isVisible()) {
                    return;
                }
                if (paused) return;
                vec.x -= player.getWidth() * player.getScaleX() / 2;
                player.getActions().removeValue(moveToAction, true);
                moveToAction.reset();
                moveToAction.setPosition(vec.x - dX, vec.y - dY);
                moveToAction.setDuration(0.2f);
                player.addAction(moveToAction);
            }
        });

        rnd = CRAssetManager.getInstance().getRandom();
        for (int i = 0; i < 10; i++) {
            enemys.add(new EnemyIstrebitel());
            enemys.get(i).setRotation(180);
        }

        alramSound = CRAssetManager.getInstance().get(Res.ALRAM_SOUND);
        resetScreen();
    }

    public GameStages getGameGtage() {
        return gameGtage;
    }

    @Override
    public void show() {
        if (Gdx.app.getPreferences(Conf.OPTIONS_NAME).getBoolean(Conf.SOUND_ENABLE_PREF_KEY, true))
            alramSound.play(Gdx.app.getPreferences(Conf.OPTIONS_NAME).getFloat(Conf.SOUND_VOLUME_PREF_KEY, Conf.SOUD_VOLUME));
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
        CRAssetManager.getInstance().setScore(0);
        for (GameObject g : enemys) {
            g.init();
            g.setPosition(rnd.nextInt(scrW), scrH - g.getHeight() + rnd.nextInt(300));
            g.clearActions();
//            g.setDebug(true);
            g.addAction(Actions.moveTo(rnd.nextInt(scrW), -200, (rnd.nextInt(100) + 50) / 10.f));
            stage.addActor(g);
        }
        resetScreen();

        Gdx.input.setCatchBackKey(true);
//        Gdx.input.setInputProcessor(stage);
    }

    private void resetScreen() {
        player.setPosition(scrW / 2, -player.getHeight());

        MoveToAction action = new MoveToAction();
        action.setDuration(1);
        action.setPosition(scrW / 2 - player.getWidth() / 2, 30);
        player.clearActions();
//        player.setOrigin(player.getWidth() / 2, player.getHeight() / 2);
        player.addAction(action);
        gameGtage = GameStages.BEGIN;

//        Gdx.input.setInputProcessor(new InputHandler(this));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!paused) {
            stage.act(delta);
        }
        if (exitDialog.isVisible()) {
            exitDialog.act(delta);
        }
        stage.draw();
        if (CartoonRaider.DEBUG) {
            for (Actor a : stage.getActors()) {
                if (a instanceof GameObject) {
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.polygon(((GameObject) a).getBoundingPolygon().getTransformedVertices());
                    shapeRenderer.x(a.getX(), a.getY(), 5);
                    shapeRenderer.circle(a.getOriginX() + a.getX(), a.getOriginY() + a.getY(), 5);
                    shapeRenderer.setColor(Color.GREEN);
                    shapeRenderer.circle(a.getX() - a.getWidth() * a.getScaleX() / 2, a.getY() - a.getHeight() * a.getScaleY() / 2, 5);
                    shapeRenderer.setColor(Color.BLUE);
                    Rectangle r = ((GameObject) a).getBoundingPolygon().getBoundingRectangle();
                    shapeRenderer.rect(r.x, r.y, r.width, r.height);
                    shapeRenderer.end();
                }
            }
        }

        switch (gameGtage) {
            case BEGIN:
                if (CRAssetManager.getInstance().getScore() > GameStages.BOSS1_BATTLE.beginScore) {
                    gameGtage = GameStages.BOSS1_BATTLE;
                    boss1 = new Boss1();
                    boss1.setPosition(scrW / 2.0f - boss1.getWidth() / 2.0f * boss1.getScaleX(), scrH);
                    boss1.addAction(Actions.moveTo(boss1.getX(), scrH - boss1.getHeight() * boss1.getScaleY(), Conf.BOSS_MOVE_TIME));
                    stage.addActor(boss1);
                }
                break;
            case BOSS1_BATTLE:
                if (boss1.getState() == GameObject.GOState.DEAD)
                    gameGtage = GameStages.STAGE1;
                break;
            case STAGE1:
                if (CRAssetManager.getInstance().getScore() > GameStages.BOSS2_BATTLE.beginScore) {
                    gameGtage = GameStages.BOSS2_BATTLE;
                    boss2 = new Boss1();
                    boss2.setPosition(scrW / 2.0f - boss2.getWidth() / 2.0f * boss2.getScaleX(), -boss2.getHeight());
                    boss2.addAction(Actions.moveTo(boss2.getX(), scrH - boss2.getHeight() * boss2.getScaleY(), Conf.BOSS_MOVE_TIME * 3));
                    stage.addActor(boss2);
                }
                break;
            default:
                break;
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
                        if (g1.checkCollision(g2)) {
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
        stage.dispose();
    }

    public enum GameStages {
        BEGIN(0),
        BOSS1_BATTLE(Conf.BOSS1_BATTLE_BEGIN_SCORE),
        STAGE1(0),
        BOSS2_BATTLE(Conf.BOSS2_BATTLE_BEGIN_SCORE);

        int beginScore;

        GameStages(int beginScore) {
            this.beginScore = beginScore;
        }
    }


}
