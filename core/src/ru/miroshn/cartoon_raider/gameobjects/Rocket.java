package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

import static com.badlogic.gdx.math.MathUtils.*;
import static java.lang.Math.abs;

/**
 * Created by miroshn on 29.04.15.
 * Ракета
 */
public class Rocket extends GameObject {
    //    private float currSpeed;
    private final float maxLinearAcceleration = 100.0f; //точек в секунду
    private int damagePower;
    private float lifeTime;
    private float distToTarget;
    private float currAngle;
    private IntAction maxAngleSpeed; // градусы в секунду
    private IntAction currSpeed;

    private Actor target;

    public Rocket() {
        super();
        damagePower = 40;
        lifeTime = 5.0f;
        setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.ROCKET));

        currSpeed = new IntAction(150, 700);
        currSpeed.setInterpolation(Interpolation.exp10);
        currSpeed.setDuration(1f);
        currAngle = 0;
        maxAngleSpeed = new IntAction(0, 180);
        maxAngleSpeed.setDuration(1.5f);

//        currSpeed = 10;
    }

    @Override
    public void act(float delta) {
        delta = 0.01f;
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

                setPosition(getX() - currSpeed.getValue() * delta * sin(angle * degreesToRadians),
                        getY() + currSpeed.getValue() * delta * cos(angle * degreesToRadians));

                currSpeed.act(delta);
                maxAngleSpeed.act(delta);
                break;
            case DEAD:
                break;
            case EXPLODING:
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
                    distToTarget = abs(getX() - a.getX()) + abs(getY() - a.getY());
                }
            }
        }
    }

    @Override
    public GameObjects who() {
        return GameObjects.ROCKET;
    }

    @Override
    public void contact(GameObject gameObject) {
        if (getState() != GOState.NORMAL) return;
        switch (gameObject.who()) {
            case ENEMY_BULLET:
                setState(GOState.EXPLODING);
                break;
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

}
