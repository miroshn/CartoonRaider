package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import ru.miroshn.cartoon_raider.CartoonRaider;

/**
 * Created by miroshn on 16.04.15.
 * Снаряд выпущенный игроком
 */
public class PlayerBullet extends Bullet {

    private PlayerBullet() {
        super();
        setColor(CartoonRaider.NORMAL_COLOR);
    }


    @Override
    public void act(float delta) {
        if (getY() > Gdx.graphics.getHeight())
            setState(GOState.DEAD);
        if (this.getState() == GOState.DEAD)
            getStage().getActors().removeValue(this, true);
        super.act(delta);
//        Array<Actor> actors = getStage().getActors();
//        for (Actor a : actors) {
//            if (a instanceof GameObject && !(a instanceof Bullet) && !(a instanceof Istrebitel)) {
//                if (((GameObject) a).getBoundingPolygon().overlaps(this.getBoundingPolygon()) && ((GameObject) a).getState() == GOState.NORMAL) {
//                    ((GameObject) a).damageDeal(getDamagePower());
//                    this.setState(GOState.DEAD);
//                }
//            }
//        }

    }

    @Override
    public GameObjects who() {
        return GameObjects.PLAYER_BULLET;
    }

    @Override
    public boolean processCollision(GameObjects gameObjects) {
        boolean ret = false;
        switch (gameObjects) {
            case BOSS1:
            case ENEMY_ISTREBITEL:
                ret = true;
                break;
        }
        return ret;
    }

    @Override
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case BOSS1:
            case ENEMY_ISTREBITEL:
                if (getState() != GOState.NORMAL) break;
                if (gameObject.getState() != GOState.NORMAL) break;
                gameObject.damageDeal(getDamagePower());
                this.setState(GOState.DEAD);
                this.clearActions();
                break;
            default:
        }
    }
}
