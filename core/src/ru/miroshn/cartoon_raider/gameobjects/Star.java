package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pools;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by miroshn on 17.04.15.
 * объект бонусов
 */
public class Star extends GameObject {

    private int power;

    private Star() {
        super();
        setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.STAR));
        setColor(CartoonRaider.NORMAL_COLOR);
    }

    public static Star createInstance() {
        Star star = Pools.obtain(Star.class);
        star.init();
        return star;
    }

    @Override
    public void init() {
        power = getRnd().nextInt(90) + 10;
        this.clearActions();
        this.addAction(Actions.moveBy(0, -Gdx.graphics.getHeight() * 2, 20));
        setState(GOState.IMMUN);
        super.init();
    }

    @Override
    public void act(float delta) {
        if (getState() == GOState.DEAD)
            this.getStage().getActors().removeValue(this, true);

        super.act(delta);
        if (getY() < -getHeight()) {
            this.clearActions();
            this.getStage().getActors().removeValue(this, true);
        }
    }

    public int getPower() {
        return power;
    }

    @Override
    public GameObjects who() {
        return GameObjects.STAR;
    }

    @Override
    public boolean processCollision(GameObjects gameObjects) {
        boolean ret = false;
        switch (gameObjects) {
            case PLAYER:
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
            default:
        }

    }
}
