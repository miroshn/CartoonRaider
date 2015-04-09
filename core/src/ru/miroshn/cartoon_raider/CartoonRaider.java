package ru.miroshn.cartoon_raider;

import com.badlogic.gdx.Game;
import ru.miroshn.cartoon_raider.screens.CustomScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

public class CartoonRaider extends Game {

    @Override
    public void create() {
        ScreenManager.getInstance().init(this);
        ScreenManager.getInstance().show(CustomScreen.LOAD_SCREEN);
//        setScreen(new WelcomeScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
    }
}
