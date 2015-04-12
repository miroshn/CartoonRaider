package ru.miroshn.cartoon_raider.helpers;

import com.badlogic.gdx.Screen;

/**
 * Created by miroshn on 10.04.15.
 */
public interface ScreenInput extends Screen {
    public boolean OnClick(int screenX, int screenY, int pointer, int button);
}
