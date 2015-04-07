package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.screens.GameOverScreen;

import java.util.Random;

/**
 * Created by miroshn on 06.04.15.
 *
 */
public class GameWorld implements Disposable {
    private Istrebitel player;

    private Array<GameObject> gameObjects;
    private CartoonRaider game;

    private SpriteBatch batch;

    public GameWorld(SpriteBatch batch, CartoonRaider game) {
        this.batch = batch;
        this.game = game;
        player = new Istrebitel();
        player.setScale(0.5f);

        gameObjects = new Array<GameObject>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            gameObjects.add(new Istrebitel());
            gameObjects.get(i).setColor(Color.BLACK);
            gameObjects.get(i).setRotation(180);
            gameObjects.get(i).setScale(0.5f);
            gameObjects.get(i).setPosition(r.nextInt(Gdx.graphics.getWidth()) - Gdx.graphics.getWidth() / 2,
                    r.nextInt(Gdx.graphics.getHeight()) + Gdx.graphics.getHeight());
            gameObjects.get(i).moveTo(r.nextInt(1000) - 500, r.nextInt(700) - 350);
        }
    }

    public void update(float delta) {
        for (GameObject g : gameObjects) {
            g.update(delta);
            if (g.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                game.setScreen(new GameOverScreen(game));
            }
        }
    }

    public void render(float delta) {
        update(delta);
        player.draw(batch);
        for (GameObject g : gameObjects) {
            g.draw(batch);
        }
    }

    @Override
    public void dispose() {
        player.dispose();
        for (GameObject g : gameObjects) g.dispose();
    }
}
