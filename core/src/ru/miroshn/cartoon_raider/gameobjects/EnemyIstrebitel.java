package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;

import java.util.Random;

/**
 * Created by miroshn on 07.04.15.
 * Класс вражеского истребителя
 */
public class EnemyIstrebitel extends GameObject implements Disposable {

    private final float BULLET_FIRE_TIME = 1;
    private Random rnd;
    private int bulletPrc;
    private float bulletTime;

    public EnemyIstrebitel() {
        super();

        bulletTime = BULLET_FIRE_TIME;
        bulletPrc = 5;

        if (rnd == null) rnd = new Random();
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
        float ver[] = {0, 0, getWidth(), 0, getWidth() / 2, getHeight()};
        setBoundingPolygon(new PolygonOverlaps(ver));
        setColor(Color.BLACK);
    }

    @Override
    public void init() {
        if (rnd == null) rnd = new Random();
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        setPosition(rnd.nextInt(Gdx.graphics.getWidth() - (int) (getWidth() * getScaleX())) + getWidth() * getScaleX(),
                Gdx.graphics.getHeight() + getHeight() + rnd.nextInt(300));
        clearActions();
        addAction(Actions.moveTo(rnd.nextInt(Gdx.graphics.getWidth() - (int) (getWidth() * getScaleX())) + getWidth() * getScaleX(), -200, (rnd.nextInt(100) + 50) / 10.f));
        super.init();
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
            default:
        }
    }

    @Override
    public GameObjects who() {
        return GameObjects.ENEMY_ISTREBITEL;
    }

    @Override
    public void act(float delta) {
        switch (getState()) {
            case NORMAL:
                bulletTime -= delta;
                if (bulletTime < 0) {
                    bulletTime = BULLET_FIRE_TIME;
                    if (rnd.nextInt(100) < bulletPrc) {
                        fireBullet();
                    }
                }
                if (getY() < -100) {
                    this.init();
                }
                break;
            case DEAD:
                Star star = new Star();
                star.setPosition(getX() - getWidth() * getScaleX() / 2, getY() - getHeight() * getScaleY() / 2);
                getStage().addActor(star);
                break;
            default:
        }
        super.act(delta);
    }

    private void fireBullet() {
        EnemyBullet bullet = new EnemyBullet();
        bullet.setPosition(getX() - getWidth() / 2 * CartoonRaider.SCALE, getY() - getHeight() * CartoonRaider.SCALE);
        bullet.setScale(CartoonRaider.SCALE);
//        bullet.addAction(Actions.moveBy(0, Gdx.graphics.getHeight()*2,5f));
        bullet.addAction(Actions.moveBy(0, -Gdx.graphics.getHeight() * 2, 3f));
        this.getStage().addActor(bullet);
    }

    @Override
    public void dispose() {
    }
}
