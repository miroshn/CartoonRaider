package ru.miroshn.cartoon_raider.screens;

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
    private CartoonRaider game;
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

    public void init(CartoonRaider game) {
        this.game = game;
    }

    public void show(CustomScreen screen) {
        if (game == null) return;
        if (!screens.containsKey(screen.ordinal())) {
            screens.put(screen.ordinal(), screen.getScreenInstance());
        }
        game.setScreen(screens.get(screen.ordinal()));
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

