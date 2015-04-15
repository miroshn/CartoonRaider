package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.screens.GameScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

/**
 * Created by miroshn on 15.04.15.
 * отображение информации на игровом экране
 */
public class Hud extends Actor {
    private BitmapFont font;
    private int score;

    public Hud() {
        super();
        score = 0;
        font = new BitmapFont();
        font.setColor(Color.BLUE);
        setPosition(5, Gdx.graphics.getHeight() - font.getCapHeight());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (ScreenManager.getInstance().getCurrentScreen() instanceof GameScreen)
            score = ((GameScreen) (ScreenManager.getInstance().getCurrentScreen())).getScore();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, "Score: " + score, getX(), getY());
    }
}
