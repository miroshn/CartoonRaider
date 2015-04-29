package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 29.04.15.
 * Ракета
 */
public class Rocket extends GameObject {
    private int damegePower;
    private float lifeTime;

    public Rocket() {
        super();
        damegePower = 40;
        lifeTime = 5.0f;
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("rocket.png")));
    }

    @Override
    public void act(float delta) {
        lifeTime -= delta;
        if (lifeTime < 0) setState(GOState.DEAD);
        super.act(delta);
    }

    @Override
    public GameObjects who() {
        return GameObjects.ROCKET;
    }

    @Override
    public void contact(GameObject gameObject) {

    }

    public int getDamegePower() {
        return damegePower;
    }

}
