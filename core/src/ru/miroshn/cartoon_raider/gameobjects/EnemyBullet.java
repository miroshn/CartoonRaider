package ru.miroshn.cartoon_raider.gameobjects;

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
    }

    @Override
    public GameObjects who() {
        return GameObjects.ENEMY_BULLET;
    }

    @Override
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case PLAYER:
                gameObject.damageDeal(getDamagePower());
                setState(GOState.DEAD);
                break;
            default:
                break;
        }
    }
}
