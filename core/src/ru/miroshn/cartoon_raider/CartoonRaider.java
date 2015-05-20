package ru.miroshn.cartoon_raider;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.screens.CustomScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

public class CartoonRaider extends Game {
    /*
    * Различные константы использующиеся в игре
    * */

    //                public static final boolean DEBUG = true;
    public static final boolean DEBUG = Conf.DEBUG;
    public static final Color NORMAL_COLOR = new Color(0, 0, 1, 1);
    public static final Color ENEMY_COLOR = new Color(0, 0, 0, 1);
    public static final Color BOSS_COLOR = new Color(1, 0, 0, 1);
    public static float SCALE;

    @Override
    public void create() {
        SCALE = Gdx.graphics.getWidth() / 640.0f;
        ScreenManager.getInstance().show(CustomScreen.LOAD_SCREEN);
    }

//    @Override
//    public void resize(int width, int height) {
//        super.resize(width, height);
//    }

    @Override
    public void dispose() {
        ScreenManager.getInstance().dispose();
        CRAssetManager.getInstance().dispose();
        Background.getInstance().dispose();
    }
}
