package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

import static java.lang.Math.abs;

/**
 * Created by miroshn on 29.04.15.
 * Ракета
 */
public class Rocket extends GameObject {
    private final int MAX_SPEED = 1000;
    private int damagePower;
    private float lifeTime;
    private IntAction speedAction;
    private MoveByAction moveByAction;
    private float distToTarget;

    private MoveToAction moveToAction;
    private Actor target;

    public Rocket() {
        super();
        damagePower = 40;
        lifeTime = 5.0f;
        setTextureRegion(((TextureAtlas) CRAssetManager.getInstance().get("CartoonRaider.pack")).findRegion("rocket"));
        speedAction = new IntAction(0, MAX_SPEED);
        speedAction.setDuration(1f);

        moveByAction = new MoveByAction();
        moveToAction = new MoveToAction();
    }

    @Override
    public void act(float delta) {
        if (target == null) searchTarget();
        lifeTime -= delta;
        if (lifeTime < 0) setState(GOState.DEAD);

        getActions().removeValue(moveToAction, true);
        float durationComplete = moveToAction.getTime();
        moveToAction.reset();
//        if (distToTarget - abs(getX() - target.getX()) + abs(getY() - target.getY()) > 10) {
//            Gdx.app.log(getClass().getSimpleName(),"dist = " + distToTarget + abs(getX() - target.getX()) + abs(getY() - target.getY())));
//            setState(GOState.DEAD);
//        }
        switch (getState()) {
            case NORMAL:
//              todo: Найти ближайшего противника, вектор направления изначально вперед, плавно изменять вектор на противника


//                moveByAction.setAmountY((float) (speedAction.getValue()));
//                moveByAction.setDuration(1.f);
//                addAction(moveByAction);
//                float dx = (getX() + getWidth()*getScaleX()/2 - target.getX() - target.getWidth()*target.getScaleX() / 2);
//                float dy = (target.getY() - target.getHeight() * target.getScaleY() / 2 - getY() + getHeight() * getScaleY() / 2);

                float dx = (getX() - target.getX() + target.getWidth() * target.getScaleX() / 2);
                float dy = (target.getY() - target.getHeight() * target.getScaleY() / 2 - getY());
                float angle = MathUtils.radiansToDegrees * MathUtils.atan2(dx, dy);
//                float angle  = MathUtils.radiansToDegrees * (float)Math.atan(dx/dy);
                this.setRotation(angle);
                moveToAction.setPosition(target.getX() - target.getWidth() * target.getScaleX() / 2,
                        target.getY() - target.getHeight() * target.getScaleY() / 2);
                moveToAction.setDuration(10);
                moveToAction.setTime(durationComplete);
                addAction(moveToAction);
                break;
            case DEAD:
                break;
            case EXPLODING:
                break;
            case IMMUN:
                break;
        }


//        setY(getY() + speedAction.getValue() * delta);


        super.act(delta);
        speedAction.act(delta);

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
