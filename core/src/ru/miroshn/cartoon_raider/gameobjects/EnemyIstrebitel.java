package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

import java.util.Random;

/**
 * Created by miroshn on 07.04.15.
 * Класс вражеского истребителя
 */
public class EnemyIstrebitel extends GameObject implements Disposable {

    private Texture texture;
    private TextureRegion region;
    private Random rnd;

    public EnemyIstrebitel() {
        rnd = new Random();
        texture = CRAssetManager.getInstance().get("istrebitel1.png");
        region = new TextureRegion(texture);
        setTextureRegion(region);
        setSize(texture.getWidth(), texture.getHeight());
        setColor(Color.BLACK);
    }

    @Override
    public Rectangle getBoundsRectangle() {
        Rectangle rec = super.getBoundsRectangle();
        rec.set(getX(), getY() - getHeight(), getWidth(), getHeight());
        return rec;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getY() < -100) {
            setPosition(rnd.nextInt(Gdx.graphics.getWidth()),
                    Gdx.graphics.getHeight() - getHeight() + rnd.nextInt(300));
            clearActions();
            addAction(Actions.moveTo(rnd.nextInt(Gdx.graphics.getWidth()), -200, rnd.nextInt(100) / 10.f));
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
