package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
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
        if (getY() > Gdx.graphics.getHeight())
            setState(GOState.DEAD);
        if (this.getState() == GOState.DEAD)
            getStage().getActors().removeValue(this, true);
        super.act(delta);
        Array<Actor> actors = getStage().getActors();
        for (Actor a : actors) {
            if (a instanceof GameObject && !(a instanceof Bullet) && !(a instanceof Istrebitel)) {
                if (((GameObject) a).getBoundingPolygon().overlaps(this.getBoundingPolygon()) && ((GameObject) a).getState() == GOState.NORMAL) {
                    ((GameObject) a).damageDeal(damagePower);
//                    ((GameObject) a).setState(GOState.EXPLODING);
                    this.setState(GOState.DEAD);
                }
            }
        }
    }
}
