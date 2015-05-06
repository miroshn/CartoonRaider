package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 08.04.15.
 * фон игры
 */
public class Background extends Actor implements Disposable {
    private static Background instance;
    private TextureRegion region;
    private MoveByAction action;
    private int SPEED_SCROLL = 5;


    private Background() {
        region = ((TextureAtlas) CRAssetManager.getInstance().get("CartoonRaider.pack")).findRegion("background");
        setPosition(0, 0);
        setSize(region.getRegionWidth(), region.getRegionHeight());
        this.addAction(createAction());

    }

    public static Background getInstance() {
        if (instance == null) instance = new Background();
        return instance;
    }

    private MoveByAction createAction() {
        action = new MoveByAction();
        action.setAmountY(-region.getRegionHeight());
        action.setDuration(SPEED_SCROLL);
        return action;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int x = 0; x < Gdx.graphics.getWidth(); x += region.getRegionWidth()) {
            for (int y = 0; y < Gdx.graphics.getHeight() * 2; y += region.getRegionHeight()) {
                batch.draw(region, getX() + x, getY() + y, getWidth(), getHeight(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        Gdx.app.log("Debug", "Actions size = " + getActions().size);
        if (getY() <= -getHeight()) {
            setPosition(0, 0);

            this.removeAction(action);
            this.addAction(createAction());
        }
    }

    @Override
    public void dispose() {
//        texture.dispose();
        instance = null;
    }
}
