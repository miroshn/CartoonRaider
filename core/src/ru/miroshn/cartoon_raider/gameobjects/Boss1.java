package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by miroshn on 19.05.15.
 * Первый в игре босс
 */
public class Boss1 extends GameObject {
    private final float BULLET_FIRE_TIME = Conf.BOSS1_BULLET_FIRE_TIME;
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
            float tox = MathUtils.random(moveToX);
            float toy = Gdx.graphics.getHeight() / 2.0f + MathUtils.random(moveToY);
            addAction(Actions.moveTo(tox, toy, Conf.BOSS_MOVE_TIME));
        }
        oldx = getX();
        oldy = getY();

        if (getState() == GOState.DEAD) {
            for (int i = 0; i < 5; i++) {
                Star star = new Star();
                star.setPosition(getX() + getWidth() / 2.0f, getY() + getHeight() / 2.0f);
                getStage().addActor(star);
            }
        }

        super.act(delta);
    }


    private void fireBullet() {
        Istrebitel player = CRAssetManager.getInstance().getPlayer();
        float distToPlayer = (float) Math.sqrt(Math.pow(getX() - player.getX(), 2) + Math.pow(getY() - player.getY(), 2));
        float bt = distToPlayer / Gdx.graphics.getHeight() * 1.5f;
        EnemyBullet bullet = new EnemyBullet();
        float bulletX = getX() + getWidth() / 2 * CartoonRaider.SCALE;
        float bulletY = getY() + getHeight() / 4.0f * CartoonRaider.SCALE;
        bullet.setPosition(bulletX, bulletY);
        bullet.setScale(CartoonRaider.SCALE);

        float dx = (player.getX() + player.getWidth() / 2.0f * CartoonRaider.SCALE - bulletX) * 3.0f;
        float dy = (player.getY() + player.getHeight() / 2.0f * CartoonRaider.SCALE - bulletY) * 3.0f;
        bullet.addAction(Actions.moveBy(dx, dy, bt * 3));
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
        if (!create) return super.getBoundingPolygon(false);
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
