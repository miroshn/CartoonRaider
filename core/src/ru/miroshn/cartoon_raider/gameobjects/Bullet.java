package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by miroshn on 15.04.15.
 * простой снаряд
 */
public abstract class Bullet extends GameObject {
    private final int damagePower;
    private Sound shotSound;

    public Bullet() {
        super();
        damagePower = 30;
        setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.BULLET));
        shotSound = CRAssetManager.getInstance().get(Res.SHOT_SOUND);
        shotSound.play();
    }

    public int getDamagePower() {
        return damagePower;
    }


//    @Override
//    public void act(float delta) {
//        super.act(delta);
//    }


}
