package ru.miroshn.cartoon_raider.helpers;

import com.badlogic.gdx.Screen;

/**
 * Created by miroshn on 10.04.15.
 * Классы с управлением обязаны переопределить эти методы
 */
public interface ScreenInput extends Screen {
    boolean OnClick(int screenX, int screenY, int pointer, int button);

    boolean touchDragged(int screenX, int screenY, int pointer);
}
