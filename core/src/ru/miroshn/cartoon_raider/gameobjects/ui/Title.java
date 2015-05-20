package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 11.04.15.
 * Заголовок игры
 */
public class Title extends Actor {
    private final TextureRegion region;
    private Texture titleTexture;

    public Title(Titles type) {
        region = CRAssetManager.getInstance().get(type.getRes());
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setColor(CartoonRaider.NORMAL_COLOR);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        batch.setColor(getColor());
        batch.draw(region, getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
        batch.setColor(color);
    }
}

