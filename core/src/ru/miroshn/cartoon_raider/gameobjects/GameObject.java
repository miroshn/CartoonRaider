package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;
import ru.miroshn.cartoon_raider.screens.GameScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

import java.util.Random;

/**
 * Created by miroshn on 06.04.15.
 *
 */
public abstract class GameObject extends Actor {

    private TextureRegion texture;
    private PolygonOverlaps boundingPolygon;
    private Random rnd;
    private GOState state;
    private float explodingTime;
    private int hp;
    private Animation explodeAnimation;

    public GameObject() {
        hp = 100;
        rnd = new Random();
        state = GOState.NORMAL;
        Array<TextureRegion> explodingSet = new Array<TextureRegion>();
        explodingSet.add(new TextureRegion((Texture) CRAssetManager.getInstance().get("explosive1.png")));
        explodingSet.add(new TextureRegion((Texture) CRAssetManager.getInstance().get("explosive2.png")));
        explodingSet.add(new TextureRegion((Texture) CRAssetManager.getInstance().get("explosive3.png")));
        explodingSet.add(new TextureRegion((Texture) CRAssetManager.getInstance().get("explosive2.png")));
        explodingSet.add(new TextureRegion((Texture) CRAssetManager.getInstance().get("explosive1.png")));
        explodeAnimation = new Animation(0.5f / 3f, explodingSet, Animation.PlayMode.NORMAL);
        init();
    }

    public Random getRnd() {
        return rnd;
    }

    public PolygonOverlaps getBoundingPolygon() {
        if (boundingPolygon == null) {
            boundingPolygon = new PolygonOverlaps(new float[]{0, 0, getWidth(), 0,
                    getWidth(), getHeight(), 0, 0 + getHeight()});
        }
        return boundingPolygon;
    }

    public void setBoundingPolygon(PolygonOverlaps boundingPolygon) {
        this.boundingPolygon = boundingPolygon;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (boundingPolygon == null) boundingPolygon = getBoundingPolygon();


        switch (state) {
            case NORMAL:
                break;
            case DEAD:
                init();
                break;
            case EXPLODING:
                doExplode(delta);
                break;
        }

        boundingPolygon.setOrigin(getOriginX(), getOriginY());
        boundingPolygon.setRotation(getRotation());
        boundingPolygon.setScale(getScaleX(), getScaleY());
        boundingPolygon.setPosition(getX(), getY());

    }

    private void doExplode(float delta) {
        if (explodingTime == 0) explodingTime = delta;
        explodingTime += delta;
        texture = explodeAnimation.getKeyFrame(explodingTime);
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        if (explodeAnimation.isAnimationFinished(explodingTime))
            setState(GOState.DEAD);
//        Gdx.app.log("GO","delta = "+delta);
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
        state = GOState.NORMAL;
        explodingTime = 0;
        hp = 100;
        if (texture == null) return;
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        setPosition(rnd.nextInt(Gdx.graphics.getWidth()) + getWidth() * getScaleX(),
                Gdx.graphics.getHeight() + getHeight() + rnd.nextInt(300));
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
    }

    public void damageDeal(int damage) {
        hp -= damage;
        if (hp <= 0) {
            setState(GOState.EXPLODING);
            ((GameScreen) (ScreenManager.getInstance().getCurrentScreen())).addScore(1);
        }
    }

    public abstract GameObjects who();

    public abstract void contact(GameObject gameObject);

    public enum GOState {
        NORMAL, DEAD, EXPLODING, IMMUN
    }
}
