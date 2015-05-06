package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;

/**
 * Created by miroshn on 07.04.15.
 * Класс вражеского истребителя
 */
public class EnemyIstrebitel extends GameObject implements Disposable {

    private final float BULLET_FIRE_TIME = 1;
    private int bulletPrc;
    private float bulletTime;
    private IstrebitelType istrebitelType;

    public EnemyIstrebitel() {
        super();

        bulletTime = BULLET_FIRE_TIME;
        bulletPrc = 5;

//        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
//        float ver[] = {0, 0, getWidth(), 0, getWidth() / 2, getHeight()};
//        setBoundingPolygon(new PolygonOverlaps(ver));
        setColor(Color.BLACK);
    }

    @Override
    public PolygonOverlaps getBoundingPolygon(boolean create) {
        if (!create) return super.getBoundingPolygon(false);
        if (super.getBoundingPolygon(false) == null) {
            float ver[] = null;
            switch (istrebitelType) {
                case I_16:
                    ver = new float[]{getWidth() / 2, 0, getWidth(), getHeight() - 10, 0, getHeight() - 10};
                    break;
                case Il_2:
                    ver = new float[]{getWidth() / 2, 0, getWidth(), getHeight() - 10, 0, getHeight() - 10};
                    break;
                case SU:
                    ver = new float[]{0, 0, getWidth(), 0, getWidth() / 2, getHeight()};
                    break;
            }
            setBoundingPolygon(new PolygonOverlaps(ver));
        }
        return super.getBoundingPolygon(true);
    }

    @Override
    public void init() {
        int rnd = getRnd().nextInt(CRAssetManager.getInstance().getScore() + 1) + CRAssetManager.getInstance().getScore();
        if (rnd >= 200) istrebitelType = IstrebitelType.SU;
        if (rnd < 200) istrebitelType = IstrebitelType.Il_2;
        if (rnd < 100) istrebitelType = IstrebitelType.I_16;

        switch (istrebitelType) {
            case I_16:
                setTextureRegion(((TextureAtlas) CRAssetManager.getInstance().get("CartoonRaider.pack")).findRegion("i"));
                setHp(30);
                break;
            case Il_2:
                setTextureRegion(((TextureAtlas) CRAssetManager.getInstance().get("CartoonRaider.pack")).findRegion("il-2"));
                setHp(40);
                break;
            case SU:
                setTextureRegion(((TextureAtlas) CRAssetManager.getInstance().get("CartoonRaider.pack")).findRegion("istrebitel1"));
                setHp(100);
                break;
        }
        setPosition(getRnd().nextInt(Gdx.graphics.getWidth() - (int) (getWidth() * getScaleX())) + getWidth() * getScaleX(),
                Gdx.graphics.getHeight() + getHeight() + getRnd().nextInt(300));
        clearActions();
        addAction(Actions.moveTo(getRnd().nextInt(Gdx.graphics.getWidth() - (int) (getWidth() * getScaleX())) + getWidth() * getScaleX(), -200, (getRnd().nextInt(100) + 50) / 10.f));
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
            case ROCKET:
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
                    if (getRnd().nextInt(100) < bulletPrc) {
                        fireBullet();
                    }
                }
                if (getY() < -100) {
                    this.init();
                }
                break;
            case DEAD:
//                Star star = new Star();
//                star.setPosition(getX() - getWidth() * getScaleX() / 2, getY() - getHeight() * getScaleY() / 2);
//                getStage().addActor(star);
                init();
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

    private enum IstrebitelType {
        I_16, Il_2, SU
    }
}
