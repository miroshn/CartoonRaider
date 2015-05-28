package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.Res;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static java.lang.Math.abs;

/**
 * Created by miroshn on 29.04.15.
 * Ракета
 */
public class Rocket extends GameObject {
    private final int damagePower;
    private final IntAction maxAngleSpeed; // градусы в секунду
    private final IntAction currSpeed;
    private float lifeTime;
    private float currAngle;
    private Actor target;
    private Sound rocketSound;

    private RocketFlame rocketFlame;
    private Group group;

    public Rocket() {
        super();
        damagePower = 40;
        lifeTime = 5.0f;
        setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.ROCKET));

        currSpeed = new IntAction((int) (150 * CartoonRaider.SCALE), (int) (700 * CartoonRaider.SCALE));
        currSpeed.setInterpolation(Interpolation.exp10);
        currSpeed.setDuration(1f);
        currAngle = 0;
        maxAngleSpeed = new IntAction(0, 180);
        maxAngleSpeed.setDuration(1.5f);
        setColor(CartoonRaider.NORMAL_COLOR);

        rocketFlame = new RocketFlame();
        addActor(rocketFlame);
        rocketSound = CRAssetManager.getInstance().get(Res.ROCKET_SOUND);
        rocketSound.play(Conf.SOUD_VOLUME);
//        rocketFlame.pa

//        currSpeed = 10;
    }

    @Override
    public void act(float delta) {
//        delta = 0.01f;
        if (target == null) searchTarget();
        lifeTime -= delta;
        if (lifeTime < 0) setState(GOState.DEAD);

        switch (getState()) {
            case NORMAL:
                target = null;
                searchTarget();
//              todo: Найти ближайшего противника, вектор направления изначально вперед, плавно изменять вектор на противника


                float dx = (getX() - (target.getX() - target.getWidth() * target.getScaleX() / 2));
                float dy = (target.getY() - target.getHeight() * target.getScaleY() / 2 - getY());
                float angle = MathUtils.radiansToDegrees * MathUtils.atan2(dx, dy);

                if (abs(angle - currAngle) > maxAngleSpeed.getValue() * delta) angle = (angle - currAngle) > 0 ?
                        currAngle + maxAngleSpeed.getValue() * delta : currAngle - maxAngleSpeed.getValue() * delta;

                currAngle = angle;
                //currSpeed += maxLinearAcceleration * delta;
                this.setRotation(angle);

                setPosition(getX() - currSpeed.getValue() * delta * sinDeg(angle),
                        getY() + currSpeed.getValue() * delta * cosDeg(angle));

                currSpeed.act(delta);
                maxAngleSpeed.act(delta);
                break;
            case DEAD:
                break;
            case EXPLODING:
                removeActor(rocketFlame);
                break;
            case IMMUN:
                break;
        }




        super.act(delta);

//        todo: сделать обработку перемещения ракеты, для начала с ускорением, потом с самонаведением
    }

    private void searchTarget() {
        if (getStage() == null) return;
        for (Actor a : this.getStage().getActors()) {
            if (!(a instanceof GameObject)) continue;
            if (((GameObject) a).who() == GameObjects.ENEMY_ISTREBITEL) {
                if (target == null) {
                    target = a;
                    continue;
                }
                if (((GameObject) a).getState() != GOState.NORMAL) continue;
                if (abs(getX() - a.getX()) + abs(getY() - a.getY()) < abs(getX() - target.getX()) + abs(getY() - target.getY())) {
                    target = a;
                }
            }
        }
    }

    @Override
    public GameObjects who() {
        return GameObjects.ROCKET;
    }

    @Override
    public boolean processCollision(GameObjects gameObjects) {
        boolean ret = false;
        switch (gameObjects) {
            case BOSS1:
            case ENEMY_BULLET:
            case ENEMY_ISTREBITEL:
                ret = true;
                break;
        }
        return ret;
    }

    @Override
    public void contact(GameObject gameObject) {
        if (getState() != GOState.NORMAL) return;
        switch (gameObject.who()) {
            case ENEMY_BULLET:
                setState(GOState.EXPLODING);
                break;
            case BOSS1:
            case ENEMY_ISTREBITEL:
                if (gameObject.getState() != GOState.NORMAL) break;
                setState(GOState.EXPLODING);
                clearActions();
                gameObject.damageDeal(getDamagePower());
                break;
            case PLAYER_BULLET:
                break;
            case STAR:
                break;
            case ROCKET:
                break;
        }

    }

    public int getDamagePower() {
        return damagePower;
    }


    private class RocketFlame extends GameObject {
        public RocketFlame() {
            super();
            setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.ROCKET_FLAME));
        }

        @Override
        public GameObjects who() {
            return GameObjects.ROCKET;
        }

        @Override
        public boolean processCollision(GameObjects gameObjects) {
            return false;
        }

        @Override
        public void contact(GameObject gameObject) {
        }
    }

}
