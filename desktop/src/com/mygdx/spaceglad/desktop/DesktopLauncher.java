package com.mygdx.spaceglad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.spaceglad.Core;
import com.mygdx.spaceglad.TestCam;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new Core(), config);
		new LwjglApplication(new TestCam(), config);
	}
}
