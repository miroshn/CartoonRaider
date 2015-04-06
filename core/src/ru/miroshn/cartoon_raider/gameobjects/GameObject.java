package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by miroshn on 06.04.15.
 */
public class GameObject extends Sprite implements Disposable {
    protected SpriteBatch batch;
    protected Vector3 pos;
    protected Vector3 moveToPos;
    protected float speed;


    public GameObject(String internalPath) {
        super(new Texture(internalPath));
//        this.batch = batch;
        pos = new Vector3(0, 0, 0);
        moveToPos = pos;
        speed = 1;
    }

    public void draw() {
        batch.draw(this, pos.x, pos.y);
    }

    public void setPos(Vector3 pos) {
        this.pos.set(pos);
    }

    public void moveTo(Vector3 pos) {
        moveToPos.set(pos);
    }

    public void update(float delta) {
        Vector3 tmp = moveToPos.cpy();
        tmp.sub(pos);
        tmp.x /= 1 / delta;
        tmp.y /= 1 / delta;
        tmp.z /= 1 / delta;
        pos.add(tmp);
    }

    @Override
    public void dispose() {
        getTexture().dispose();
    }
}
