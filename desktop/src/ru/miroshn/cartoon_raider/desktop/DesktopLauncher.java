package ru.miroshn.cartoon_raider.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.Conf;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = Conf.DESKTOP_WIDTH;
		config.height = Conf.DESKTOP_HEIGHT;

		new LwjglApplication(new CartoonRaider(), config);
	}
}
