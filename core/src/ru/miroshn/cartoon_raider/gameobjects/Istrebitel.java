package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by miroshn on 06.04.15.
 * главное действующее лицо
 */
public class Istrebitel extends GameObject {

    public Istrebitel(SpriteBatch batch) {
        super("istrebitel1.png");
        this.batch = batch;
        pos = new Vector3(-getWidth() / 2, 0, 0);
    }
}
