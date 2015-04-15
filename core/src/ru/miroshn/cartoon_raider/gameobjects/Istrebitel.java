package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.screens.CustomScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

/**
 * Created by miroshn on 06.04.15.
 * главное действующее лицо
 */
public class Istrebitel extends GameObject implements Disposable {
    private float speedBulletFire;
    private float bulletTime;

    public Istrebitel() {
        super();
        speedBulletFire = 1;
        bulletTime = 0f;
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
//        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
//        float ver[] = {getX(), getY()
//                , getX() + getWidth(), getY()
//                , getX() + getWidth() / 2, getY() + getHeight()};
//        setBoundingPolygon(new PolygonOverlaps(ver));
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
                break;
            case EXPLODING:
                this.clearActions();
                break;
        }
        super.act(delta);
    }

    private void fireBullet() {
        Bullet bullet = new Bullet();
        bullet.setPosition(getX() + getWidth() / 2, getY() + getHeight());
        bullet.setScale(0.5f);
//        bullet.addAction(Actions.moveBy(0, Gdx.graphics.getHeight()*2,5f));
        bullet.addAction(Actions.moveBy(0, Gdx.graphics.getHeight() * 2, 2f));
        this.getStage().addActor(bullet);
    }


    @Override
    public void init() {
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        super.init();
    }

    @Override
    public void dispose() {
    }
}
