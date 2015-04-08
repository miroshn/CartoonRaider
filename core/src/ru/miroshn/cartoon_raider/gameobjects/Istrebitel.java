package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by miroshn on 06.04.15.
 * главное действующее лицо
 */
public class Istrebitel extends GameObject {
    private Texture texture;
    private TextureRegion region;

    public Istrebitel() {
        texture = new Texture("istrebitel1.png");
        region = new TextureRegion(texture);
        setTextureRegion(region);
        setSize(texture.getWidth(), texture.getHeight());
    }
}
