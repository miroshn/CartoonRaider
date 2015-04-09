package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by miroshn on 08.04.15.
 * фон игры
 */
public class Background extends Actor implements Disposable {
    private Texture texture;
    private TextureRegion region;
    private MoveByAction action;
    private int SPEED_SCROLL = 5;

    public Background() {
        texture = new Texture("background.jpg");
        region = new TextureRegion(texture);
        setPosition(0, 0);
        setSize(texture.getWidth(), texture.getHeight());
        this.addAction(createAction());

    }

    private MoveByAction createAction() {
        action = new MoveByAction();
        action.setAmountY(-texture.getHeight());
        action.setDuration(SPEED_SCROLL);
        return action;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int x = 0; x < Gdx.graphics.getWidth(); x += texture.getWidth()) {
            for (int y = 0; y < Gdx.graphics.getHeight() * 2; y += texture.getHeight()) {
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
        texture.dispose();
    }
}
