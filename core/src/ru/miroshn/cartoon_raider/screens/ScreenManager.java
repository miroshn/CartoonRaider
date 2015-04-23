package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import ru.miroshn.cartoon_raider.CartoonRaider;

/**
 * Created by miroshn on 09.04.15.
 * менеджер экранов
 */
public class ScreenManager implements Disposable {
    private static ScreenManager instance;
    private IntMap<Screen> screens;

    private ScreenManager() {
        screens = new IntMap<Screen>();
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void show(CustomScreen screen) {
        if (Gdx.app.getApplicationListener() == null) return;
        if (!screens.containsKey(screen.ordinal())) {
            screens.put(screen.ordinal(), screen.getScreenInstance());
        }
        ((CartoonRaider) Gdx.app.getApplicationListener()).setScreen(screens.get(screen.ordinal()));
    }

    public void dispose(CustomScreen screen) {
        if (!screens.containsKey(screen.ordinal())) return;
        screens.remove(screen.ordinal()).dispose();
    }

    @Override
    public void dispose() {
        for (com.badlogic.gdx.Screen screen : screens.values()) {
            screen.dispose();
        }
        screens.clear();
        instance = null;
    }
}

