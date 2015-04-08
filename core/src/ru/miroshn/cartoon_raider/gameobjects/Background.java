package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by miroshn on 08.04.15.
 */
public class Background extends Actor implements Disposable {
    Texture texture;
    TextureRegion region;
    MoveToAction action;

    public Background() {
        texture = new Texture("background.jpg");
        region = new TextureRegion(texture);
        setPosition(0, 0);
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int x = 0; x < Gdx.graphics.getWidth(); x += texture.getWidth()) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y += texture.getHeight()) {
                batch.draw(region, getX() + x, getY() + y, getWidth(), getHeight(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getActions().size == 0) {
            action = new MoveToAction();
            action.setPosition(getX(), -texture.getHeight());
            action.setDuration(2);
            addAction(action);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
