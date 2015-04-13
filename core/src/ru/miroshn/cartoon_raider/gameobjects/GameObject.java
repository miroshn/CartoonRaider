package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;

/**
 * Created by miroshn on 06.04.15.
 *
 */
public class GameObject extends Actor {

    private TextureRegion texture;
    private Rectangle boundsRectangle;
    private PolygonOverlaps boundingPolygon;

    public GameObject() {
    }

    public GameObject(TextureRegion texture) {
        this.texture = texture;
    }

    public PolygonOverlaps getBoundingPolygon() {
        if (boundingPolygon == null) {
            Rectangle r = getBoundsRectangle();
            boundingPolygon = new PolygonOverlaps(new float[]{r.getX(), r.getY(), r.getX() + r.getWidth(), r.getY(),
                    r.getX() + r.getWidth(), r.getY() + r.getWidth(), r.getX(), r.getY() + r.getHeight()});
        }
        return boundingPolygon;
    }

    public void setBoundingPolygon(PolygonOverlaps boundingPolygon) {
        this.boundingPolygon = boundingPolygon;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (boundsRectangle == null) boundsRectangle = getBoundsRectangle();
        if (boundingPolygon == null) boundingPolygon = getBoundingPolygon();

        boundsRectangle.set(getOriginX() * getScaleX() + getX(), getOriginY() * getScaleY() + getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
        boundingPolygon.setPosition(getOriginX() * getScaleX() + getX(), getOriginY() * getScaleY() + getY());
        boundingPolygon.setScale(getScaleX(), getScaleY());
        boundingPolygon.setRotation(getRotation());
    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public void setTextureRegion(TextureRegion texture) {
        this.texture = texture;
    }

    public Rectangle getBoundsRectangle() {
        if (boundsRectangle == null) boundsRectangle = new Rectangle();
        return boundsRectangle;
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
}
