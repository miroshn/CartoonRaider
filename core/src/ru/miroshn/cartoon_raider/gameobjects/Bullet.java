package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 15.04.15.
 * простой снаряд
 */
public abstract class Bullet extends GameObject {
    private int damagePower;

    public Bullet() {
        super();
        damagePower = 30;
        setTextureRegion(((TextureAtlas) CRAssetManager.getInstance().get("CartoonRaider.pack")).findRegion("bullet"));
    }

    public int getDamagePower() {
        return damagePower;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }


}
