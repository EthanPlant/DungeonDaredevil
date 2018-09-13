package pjkck.dungeondaredevil.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pjkck.dungeondaredevil.GamDungeonDaredevil;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen=true;
		new LwjglApplication(new GamDungeonDaredevil(), config);
	}
}
