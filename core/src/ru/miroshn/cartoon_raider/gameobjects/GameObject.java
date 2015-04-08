package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by miroshn on 06.04.15.
 *
 */
public class GameObject extends Actor {

    private TextureRegion texture;
//    protected float moveToX;
//    protected float moveToY;


    public GameObject() {
    }

    public GameObject(TextureRegion texture) {
        this.texture = texture;
    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public void setTextureRegion(TextureRegion texture) {
        this.texture = texture;
    }


//    public void moveTo(float x, float y) {
//        moveToX = x;
//        moveToY = y;
//    }


//    public void update(float delta) {
//        float tmpx = moveToX;
//        float tmpy = moveToY;
//        tmpx -= getX();
//        tmpy -= getY();
//        tmpx /= 1 / delta;
//        tmpy /= 1 / delta;
//        setX(getX() + tmpx);
//        setY(getY() + tmpy);
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
