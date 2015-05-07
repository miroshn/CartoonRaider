package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 17.04.15.
 * объект бонусов
 */
public class Star extends GameObject {

    private int power;

    public Star() {
        super();
        power = getRnd().nextInt(90) + 10;
        setTextureRegion(((TextureAtlas) CRAssetManager.getInstance().get("CartoonRaider.pack")).findRegion("stars"));
        this.addAction(Actions.moveBy(0, -Gdx.graphics.getHeight() * 2, 20));
        setState(GOState.IMMUN);
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
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case PLAYER:
                gameObject.contact(this);
                break;
            default:
        }

    }
}
