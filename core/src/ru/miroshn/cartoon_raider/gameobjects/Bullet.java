package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 15.04.15.
 * простой снаряд
 */
public class Bullet extends GameObject {
    public Bullet() {
        super();
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("bullet.png")));
    }
}
