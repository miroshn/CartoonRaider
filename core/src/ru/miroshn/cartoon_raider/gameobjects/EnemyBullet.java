package ru.miroshn.cartoon_raider.gameobjects;

/**
 * Created by miroshn on 16.04.15.
 * Снаряд выпущенный вражиной
 */
public class EnemyBullet extends Bullet {
    private float oldx,oldy;

    public EnemyBullet() {
        super();
        oldx = getX();
        oldy = getY();
    }

    @Override
    public void act(float delta) {
        if (oldx == getX() && oldy == getY())
            setState(GOState.DEAD);
        else {
            oldx = getX();
            oldy = getY();
        }
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
    public boolean processCollision(GameObjects gameObjects) {
        boolean ret = false;
        switch (gameObjects) {
            case PLAYER:
                ret = true;
                break;
            case ROCKET:
                ret = true;
                break;
        }
        return ret;
    }

    @Override
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case PLAYER:
                gameObject.contact(this);
                break;
            case ROCKET:
                gameObject.contact(this);
                break;
            default:
                break;
        }
    }
}
