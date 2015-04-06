package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

/**
 * Created by miroshn on 06.04.15.
 */
public class GameWorld implements Disposable {
    private Istrebitel player;

    private Array<GameObject> gameObjects;

    private SpriteBatch batch;

    public GameWorld(SpriteBatch batch) {
        this.batch = batch;
        player = new Istrebitel(this.batch);

        gameObjects = new Array<GameObject>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            gameObjects.add(new Istrebitel(batch));
            gameObjects.get(i).setPosition(r.nextInt(1000) - 500, r.nextInt(700) - 350);
            gameObjects.get(i).moveTo(r.nextInt(1000) - 500, r.nextInt(700) - 350);
        }
    }

    public void update() {

    }

    public void render(float delta) {
        player.draw(batch);
        for (GameObject g : gameObjects) {
            g.update(delta);
            g.draw(batch);
        }
    }

    @Override
    public void dispose() {
        player.dispose();
        for (GameObject g : gameObjects) g.dispose();
    }
}
