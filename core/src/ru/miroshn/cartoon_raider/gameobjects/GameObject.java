package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;
import ru.miroshn.cartoon_raider.helpers.Res;

import java.util.Random;

/**
 * Created by miroshn on 06.04.15.
 *
 */
public abstract class GameObject extends Group {

    private final Random rnd;
    private final Animation explodeAnimation;
    private TextureRegion texture;
    private PolygonOverlaps boundingPolygon;
    private GOState state;
    private float explodingTime;
    private int hp;
    private Sound explodingSound;
    private boolean exPlayed = false;


    public GameObject() {
        hp = 100;
        rnd = CRAssetManager.getInstance().getRandom();
        state = GOState.NORMAL;
        Array<TextureRegion> explodingSet = new Array<TextureRegion>();
        explodingSet.add((TextureRegion) CRAssetManager.getInstance().get(Res.EXPLOSIVE1));
        explodingSet.add((TextureRegion) CRAssetManager.getInstance().get(Res.EXPLOSIVE2));
        explodingSet.add((TextureRegion) CRAssetManager.getInstance().get(Res.EXPLOSIVE3));
        explodingSet.add((TextureRegion) CRAssetManager.getInstance().get(Res.EXPLOSIVE2));
        explodingSet.add((TextureRegion) CRAssetManager.getInstance().get(Res.EXPLOSIVE1));
        explodeAnimation = new Animation(0.5f / 3f, explodingSet, Animation.PlayMode.NORMAL);
        init();
        setScale(CartoonRaider.SCALE);
        explodingSound = CRAssetManager.getInstance().get(Res.EXPLOSIVE_SOUND);
    }

    public static <T> T createInstance(Class<T> type) {
        T obj = Pools.obtain(type);
        ((GameObject) obj).init();
        return obj;
    }

    public Random getRnd() {
        return rnd;
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        boundingPolygon.setPosition(getX(), getY());
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        boundingPolygon.setRotation(getRotation());
    }

    @Override
    public void setOrigin(float originX, float originY) {
        super.setOrigin(originX, originY);
        boundingPolygon.setOrigin(originX, originY);
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        boundingPolygon.setScale(getScaleX(), getScaleY());
    }

    public PolygonOverlaps getBoundingPolygon(boolean create) {
        if (boundingPolygon == null && create) {
            boundingPolygon = new PolygonOverlaps(new float[]{0, 0, getWidth(), 0,
                    getWidth(), getHeight(), 0, 0 + getHeight()});
//            Gdx.app.log(getClass().getSimpleName(),"Created");
        }
        if (boundingPolygon == null) return null;
        return boundingPolygon;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (boundingPolygon == null) boundingPolygon = getBoundingPolygon(true);


        switch (state) {
            case NORMAL:
                break;
            case DEAD:
//                init();
                this.getStage().getActors().removeValue(this, true);
                Pools.free(this);
                break;
            case EXPLODING:
                doExplode(delta);
                break;
        }


    }

    private void doExplode(float delta) {
        if (explodingTime == 0) explodingTime = delta;
        explodingTime += delta;
        texture = explodeAnimation.getKeyFrame(explodingTime);
//        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        setSize(getHeight(), getHeight());
        if (explodeAnimation.isAnimationFinished(explodingTime))
            setState(GOState.DEAD);
        if (!exPlayed) {
            explodingSound.play(Conf.SOUD_VOLUME);
            exPlayed = true;
        }
//        Gdx.app.log("GO","delta = "+delta);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        boundingPolygon = null;
        getBoundingPolygon(true);
    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public void setTextureRegion(TextureRegion texture) {
        this.texture = texture;
        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color c = batch.getColor();
        batch.setColor(this.getColor());
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        batch.setColor(c);
    }

    public void init() {
        exPlayed = false;
        state = GOState.NORMAL;
        explodingTime = 0;
        if (hp == 0) hp = 100;
        if (texture == null) return;
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        boundingPolygon.setOrigin(getOriginX(), getOriginY());
        boundingPolygon.setRotation(getRotation());
        boundingPolygon.setScale(getScaleX(), getScaleY());
        boundingPolygon.setPosition(getX(), getY());

//        setPosition(rnd.nextInt(Gdx.graphics.getWidth()) + getWidth() * getScaleX(),
//                Gdx.graphics.getHeight() + getHeight() + rnd.nextInt(300));
    }

    public GOState getState() {
        return state;
    }

    public void setState(GOState state) {
        this.state = state;
    }

    public int getHp() {
        return hp;
    }

    protected void setHp(int hp) {
        this.hp = hp;
//        if (this.hp > 100) this.hp = 100;
        if (hp <= 0) {
            setState(GOState.EXPLODING);
        }
    }

    public void damageDeal(int damage) {
        setHp(getHp() - damage);
        if (hp <= 0) {
            setState(GOState.EXPLODING);
            CRAssetManager.getInstance().addScore(1);
        }
    }

    public abstract GameObjects who();

    public abstract boolean processCollision(GameObjects gameObjects);

    public abstract void contact(GameObject gameObject);

    public boolean checkCollision(GameObject gameObject) {
        if (!processCollision(gameObject.who())) {
            return false;
        }
//        if (!getBoundingPolygon().getBoundingRectangle().overlaps(gameObject.getBoundingPolygon().getBoundingRectangle())) {
//            return false;
//        }

        return getBoundingPolygon().getBoundingRectangle().overlaps(gameObject.getBoundingPolygon().getBoundingRectangle())
                && getBoundingPolygon().overlaps(gameObject.getBoundingPolygon())
                || gameObject.getBoundingPolygon().overlaps(getBoundingPolygon());
    }

    public PolygonOverlaps getBoundingPolygon() {
        return getBoundingPolygon(true);
    }

    public void setBoundingPolygon(PolygonOverlaps boundingPolygon) {
        this.boundingPolygon = boundingPolygon;
    }

    public enum GOState {
        NORMAL, DEAD, EXPLODING, IMMUN
    }
}
