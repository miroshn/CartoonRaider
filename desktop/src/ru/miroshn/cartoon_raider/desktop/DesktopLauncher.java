package ru.miroshn.cartoon_raider.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.miroshn.cartoon_raider.CartoonRaider;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = 320;
		config.height = 480;
//		config.width = 320 * 2;
//		config.height = 480 * 2;

		new LwjglApplication(new CartoonRaider(), config);
	}
}
