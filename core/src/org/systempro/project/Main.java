package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.carviewer.TestScreen;
import org.systempro.project.coolShaders.MandelbrotSet;
import org.systempro.project.test3d.MengerSpongeTest;

public class Main extends Game {

	@Override
	public void create () {
		setScreen(new TestScreen()) ;
	}

}
