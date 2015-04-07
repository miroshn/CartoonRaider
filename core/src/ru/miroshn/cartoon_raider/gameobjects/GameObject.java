package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by miroshn on 06.04.15.
 *
 */
public class GameObject extends Sprite implements Disposable {
    protected float moveToX;
    protected float moveToY;
    protected float speed;


    public GameObject(String internalPath) {
        super(new Texture(internalPath));
        speed = 1;
    }


    public void moveTo(float x, float y) {
        moveToX = x;
        moveToY = y;
    }

    public void update(float delta) {
        float tmpx = moveToX;
        float tmpy = moveToY;
        tmpx -= getX();
        tmpy -= getY();
        tmpx /= 1 / delta;
        tmpy /= 1 / delta;
        setX(getX() + tmpx);
        setY(getY() + tmpy);
    }

    @Override
    public void dispose() {
        getTexture().dispose();
    }
}
