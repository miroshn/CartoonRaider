package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by miroshn on 16.04.15.
 * Снаряд выпущенный вражиной
 */
public class EnemyBullet extends Bullet {
    @Override
    public void act(float delta) {
        if (getY() < -getHeight())
            setState(GOState.DEAD);
        if (this.getState() == GOState.DEAD)
            getStage().getActors().removeValue(this, true);
        super.act(delta);
        Array<Actor> actors = getStage().getActors();
        for (Actor a : actors) {
            if (a instanceof Istrebitel) {
                if (((GameObject) a).getBoundingPolygon().overlaps(this.getBoundingPolygon()) && ((GameObject) a).getState() == GOState.NORMAL) {
                    ((GameObject) a).damageDeal(getDamagePower());
                    this.setState(GOState.DEAD);
                }
            }
        }

    }
}
