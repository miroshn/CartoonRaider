package ru.miroshn.cartoon_raider;

import com.badlogic.gdx.Game;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.screens.WelcomeScreen;

public class CartoonRaider extends Game {
    Background background;
//    private Vector3 tapPos;

    @Override
    public void create() {
        background = Background.getInstance();
        setScreen(new WelcomeScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        background.dispose();
        background = null;
        System.gc();
    }
}
