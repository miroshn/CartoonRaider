package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by miroshn on 16.04.15.
 * Снаряд выпущенный игроком
 */
public class PlayerBullet extends Bullet {

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
                    ((GameObject) a).damageDeal(getDamagePower());
                    this.setState(GOState.DEAD);
                }
            }
        }

    }

    @Override
    public GameObjects who() {
        return GameObjects.PLAYER_BULLET;
    }

    @Override
    public void contact(GameObject gameObject) {

    }
}
