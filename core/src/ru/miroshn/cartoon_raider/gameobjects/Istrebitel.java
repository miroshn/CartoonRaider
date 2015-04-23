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
    public static final float MAX_ROF = 0.1f;
    public static final float MIN_ROF = 0.5f;
    private float speedBulletFire;
    private float bulletTime;
    private IntAction intAction;

    public Istrebitel() {
        super();
        speedBulletFire = MIN_ROF;
        bulletTime = 0f;
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
        float ver[] = {0, 0, getWidth(), 0, getWidth() / 2, getHeight()};
        setBoundingPolygon(new PolygonOverlaps(ver));
        CRAssetManager.getInstance().setPlayer(this);
    }

    @Override
    public void act(float delta) {
        switch (getState()) {
            case DEAD:
                ScreenManager.getInstance().show(CustomScreen.GAME_OVER);
                break;
            case NORMAL:
                bulletTime += delta;
                if (bulletTime >= speedBulletFire) {
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
        speedBulletFire = MIN_ROF;
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
                    if (speedBulletFire < MAX_ROF) speedBulletFire = MAX_ROF;
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

    public float getRof() {
        return speedBulletFire;
    }
}
