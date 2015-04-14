package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;

import java.util.Random;

/**
 * Created by miroshn on 07.04.15.
 * Класс вражеского истребителя
 */
public class EnemyIstrebitel extends GameObject implements Disposable {

    private Random rnd;

    public EnemyIstrebitel() {
        rnd = new Random();
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
        setColor(Color.BLACK);
        setOrigin(getX() + getWidth() / 2, getY());
        float ver[] = {getX(), getY()
                , getX() + getWidth(), getY()
                , getX() + getWidth() / 2, getY() + getHeight()};
        setBoundingPolygon(new PolygonOverlaps(ver));
    }

//    @Override
//    public Rectangle getBoundsRectangle() {
//        Rectangle rec = super.getBoundsRectangle();
//        rec.set(getX() - getWidth() * getScaleX(), getY() - getHeight() * getScaleY(), getWidth() * getScaleX(), getHeight() * getScaleY());
//        return rec;
//    }


    @Override
    public void init() {
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("istrebitel1.png")));
        super.init();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (getY() < -100) {
            this.init();
            setPosition(rnd.nextInt(Gdx.graphics.getWidth() - (int) (getWidth() * getScaleX())) + getWidth() * getScaleX(),
                    Gdx.graphics.getHeight() + getHeight() + rnd.nextInt(300));
            clearActions();
            addAction(Actions.moveTo(rnd.nextInt(Gdx.graphics.getWidth() - (int) (getWidth() * getScaleX())) + getWidth() * getScaleX(), -200, (rnd.nextInt(100) + 50) / 10.f));
        }
    }

    @Override
    public void dispose() {
    }
}
