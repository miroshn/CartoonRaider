package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by miroshn on 08.04.15.
 */
public class Background extends Actor implements Disposable {
    Texture texture;

    public Background() {
        texture = new Texture("background.jpg");
        setPosition(0, 0);
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int x = 0; x < Gdx.graphics.getWidth(); x += texture.getWidth()) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y += texture.getHeight()) {
                batch.draw(texture, x, y);
            }
        }

    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
