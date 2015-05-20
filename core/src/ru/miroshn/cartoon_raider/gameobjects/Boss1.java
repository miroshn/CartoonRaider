package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;
import ru.miroshn.cartoon_raider.helpers.Res;

import java.util.Random;

/**
 * Created by miroshn on 19.05.15.
 * Первый в игре босс
 */
public class Boss1 extends GameObject {
    private final float BULLET_FIRE_TIME = 0.5f;
    private int bulletPrc = 70;
    private float bulletTime;

    private float oldx, oldy;
    private float moveToX, moveToY;

    public Boss1() {
        super();
        setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.BOSS1));
        setHp(1000);
        bulletTime = BULLET_FIRE_TIME;
        setColor(Color.RED);
        oldx = getX();
        oldy = getY();
    }


    @Override
    public void init() {
        super.init();
        moveToX = Gdx.graphics.getWidth() - getWidth() * getScaleX();
        moveToY = Gdx.graphics.getHeight() / 2.0f - getHeight() * getScaleY();
    }

    @Override
    public GameObjects who() {
        return GameObjects.BOSS1;
    }


    @Override
    public void act(float delta) {
        bulletTime -= delta;
        if (bulletTime < 0) {
            int ttt = getRnd().nextInt(100);
            bulletTime = BULLET_FIRE_TIME;
            if (ttt <= bulletPrc) {
                fireBullet();
            }
        }

        if (oldx == getX() && oldy == getY()) {
            Random rnd = CRAssetManager.getInstance().getRandom();
            addAction(Actions.moveTo(rnd.nextInt((int) moveToX), Gdx.graphics.getHeight() / 2.0f + rnd.nextInt((int) moveToY), 10.0f));
        }
        oldx = getX();
        oldy = getY();

        super.act(delta);
    }


    private void fireBullet() {
        Istrebitel player = CRAssetManager.getInstance().getPlayer();
        float distToPlayer = (float) Math.sqrt(Math.pow(getX() - player.getX(), 2) + Math.pow(getY() - player.getY(), 2));
        float bt = distToPlayer / Gdx.graphics.getHeight() * 1.5f;
        Gdx.app.log(getClass().getSimpleName(), "bt = " + bt);
        Gdx.app.log(getClass().getSimpleName(), "dist = " + distToPlayer);
        EnemyBullet bullet = new EnemyBullet();
        bullet.setPosition(getX() + getWidth() / 2 * CartoonRaider.SCALE, getY() + getHeight() / 4.0f * CartoonRaider.SCALE);
        bullet.setScale(CartoonRaider.SCALE);
        bullet.addAction(Actions.moveTo(player.getX() + player.getWidth() / 2.0f * CartoonRaider.SCALE,
                player.getY() + player.getHeight() / 2.0f * CartoonRaider.SCALE, bt));
        this.getStage().addActor(bullet);
    }

    @Override
    public boolean processCollision(GameObjects gameObjects) {
        boolean ret = false;
        switch (gameObjects) {
            case PLAYER:
                ret = true;
                break;
            case PLAYER_BULLET:
                ret = true;
                break;
            case ROCKET:
                ret = true;
                break;
        }
        return ret;
    }


    @Override
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case PLAYER:
                gameObject.contact(this);
                break;
            case PLAYER_BULLET:
                gameObject.contact(this);
                break;
            case ROCKET:
                gameObject.contact(this);
                break;
        }
    }

    @Override
    public PolygonOverlaps getBoundingPolygon(boolean create) {
        if (!create) return super.getBoundingPolygon(create);
        if (super.getBoundingPolygon(false) == null) {
            float[] ver = new float[]{0, getHeight() * getScaleY() * 2.0f / 3.0f
                    , getWidth() * getScaleX() / 2.0f, getHeight() * getScaleY()
                    , getWidth() * getScaleX(), getHeight() * getScaleY() * 2.0f / 3.0f
                    , getWidth() * getScaleX() / 2.0f, 0};
            setBoundingPolygon(new PolygonOverlaps(ver));
        }
        return super.getBoundingPolygon(true);
    }
}
