package ru.miroshn.cartoon_raider.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.miroshn.cartoon_raider.CartoonRaider;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
//		config.width = 1024;
//		config.height = 768;
		config.width = 1000;
		config.height = 1000;

		new LwjglApplication(new CartoonRaider(), config);
	}
}
