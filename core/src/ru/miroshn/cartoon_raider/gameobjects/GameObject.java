package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by miroshn on 06.04.15.
 *
 */
public class GameObject extends Actor {

    private TextureRegion texture;


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
