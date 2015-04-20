package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        power = getRnd().nextInt(100);
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("stars.png")));
        this.addAction(Actions.moveBy(0, -Gdx.graphics.getHeight() * 2, 20));
        setState(GOState.IMMUN);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getY() < -getHeight()) {
            this.clearActions();
            this.getStage().getActors().removeValue(this, true);
            Gdx.app.log(getClass().getSimpleName(), "Stars Destroed");
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
