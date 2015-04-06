package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by miroshn on 06.04.15.
 * главное действующее лицо
 */
public class Istrebitel extends GameObject {

    public Istrebitel(SpriteBatch batch) {
        super("istrebitel1.png");
        setX(-getWidth() / 2);
        setY(0);
    }
}
