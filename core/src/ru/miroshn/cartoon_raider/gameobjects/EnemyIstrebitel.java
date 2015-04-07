package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by miroshn on 07.04.15.
 * Класс вражеского истребителя
 */
public class EnemyIstrebitel extends GameObject {

    public EnemyIstrebitel() {
        super("istrebitel1.png");
        setColor(Color.BLACK);
        setRotation(180);
    }
}
