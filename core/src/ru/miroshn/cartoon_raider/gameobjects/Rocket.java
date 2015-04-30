package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

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

    public Rocket() {
        super();
        damagePower = 40;
        lifeTime = 5.0f;
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("rocket.png")));
        speedAction = new IntAction(0, MAX_SPEED);
        speedAction.setDuration(lifeTime);

        moveByAction = new MoveByAction();
    }

    @Override
    public void act(float delta) {
        lifeTime -= delta;
        if (lifeTime < 0) setState(GOState.DEAD);

        getActions().removeValue(moveByAction, true);
        moveByAction.reset();
        switch (getState()) {
            case NORMAL:
                moveByAction.setAmountY((float) (speedAction.getValue()));
                moveByAction.setDuration(1.f);
                addAction(moveByAction);
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
