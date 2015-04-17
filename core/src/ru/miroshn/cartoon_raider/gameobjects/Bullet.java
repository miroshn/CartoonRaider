package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 15.04.15.
 * простой снаряд
 */
public class Bullet extends GameObject {
    private int damagePower;

    public Bullet() {
        super();
        damagePower = 30;
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("bullet.png")));
    }

    public int getDamagePower() {
        return damagePower;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public GameObjects who(GameObject gameObject) {
        return GameObjects.BULLET;
    }
}
