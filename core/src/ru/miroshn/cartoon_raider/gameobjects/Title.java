package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 11.04.15.
 * Заголовок игры
 */
public class Title extends Actor implements Disposable {
    private TextureRegion region;
    private Texture titleTexture;

    public Title() {
        titleTexture = CRAssetManager.getInstance().get("title.png");
        setSize(titleTexture.getWidth(), titleTexture.getHeight());
        region = new TextureRegion(titleTexture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void dispose() {
        titleTexture.dispose();
    }
}
