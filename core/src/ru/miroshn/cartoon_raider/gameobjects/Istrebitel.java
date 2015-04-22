package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;
import ru.miroshn.cartoon_raider.screens.CustomScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

/**
 * Created by miroshn on 06.04.15.
 * главное действующее лицо
 */
public class Istrebitel extends GameObject implements Disposable {
    private float speedBulletFire;
    private float bulletTime;
    private IntAction intAction;

    public Istrebitel() {
        super();
        speedBulletFire = 0.5f;
        bulletTime = 0f;
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
        float ver[] = {0, 0, getWidth(), 0, getWidth() / 2, getHeight()};
        setBoundingPolygon(new PolygonOverlaps(ver));
    }

    @Override
    public void act(float delta) {
        switch (getState()) {
            case DEAD:
                ScreenManager.getInstance().show(CustomScreen.GAME_OVER);
                break;
            case NORMAL:
//                int score = ((GameScreen) (ScreenManager.getInstance().getCurrentScreen())).getScore();
//                if (speedBulletFire - score / 100.0f < 0.1) score = 39;

                bulletTime += delta;
                if (bulletTime >= speedBulletFire) {
//                    Gdx.app.log(toString(), "speed = " + (speedBulletFire - score / 100.0f));
                    bulletTime = 0;
                    fireBullet();
                }
                super.setHp(intAction.getValue());
                break;
            case EXPLODING:
                this.clearActions();
                super.setHp(intAction.getValue());
                setHp(0);
                break;
        }
        super.act(delta);
        Gdx.app.log(getClass().getSimpleName(), "Action size" + this.getActions().size);
    }

    private void fireBullet() {
        PlayerBullet bullet = new PlayerBullet();
        bullet.setPosition(getX() + getWidth() / 2, getY() + (getHeight() + getOriginY()) * getScaleY());
        bullet.setScale(CartoonRaider.SCALE);
//        bullet.addAction(Actions.moveBy(0, Gdx.graphics.getHeight()*2,5f));
        bullet.addAction(Actions.moveBy(0, Gdx.graphics.getHeight() * 2, 2f));
        this.getStage().addActor(bullet);
    }


    @Override
    public void init() {
        intAction = new IntAction();
        intAction.setValue(100);
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        super.init();
    }

    @Override
    public GameObjects who() {
        return GameObjects.PLAYER;
    }

    @Override
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case ENEMY_BULLET:
                damageDeal(((EnemyBullet) gameObject).getDamagePower());
                gameObject.setState(GOState.DEAD);
                break;
            case ENEMY_ISTREBITEL:
                if (gameObject.getState() == GOState.NORMAL) {
                    gameObject.setState(GOState.EXPLODING);
                    this.setState(GOState.EXPLODING);
                }
                break;
            case STAR:
                if (getHp() < 100) {
                    setHp(getHp() + ((Star) gameObject).getPower() / 10);
                } else {
                    speedBulletFire -= ((Star) gameObject).getPower() / 10000.0f;
                    if (speedBulletFire < 0.1f) speedBulletFire = 0.1f;
                    Gdx.app.log(getClass().getSimpleName(), "speedBulletFire = " + speedBulletFire);
                }
                gameObject.setState(GOState.DEAD);

                break;
            default:
                break;
        }
    }

    @Override
    protected void setHp(int hp) {
//        this.getActions().removeValue(intAction,true);
        intAction.reset();
        intAction.setStart(getHp());
        intAction.setEnd(hp);
        intAction.setDuration(0.5f);
        intAction.setValue(getHp());
        this.addAction(intAction);
    }

    @Override
    public void dispose() {
    }
}
