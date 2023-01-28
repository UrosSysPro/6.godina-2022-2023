package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.renderers.ShadowTest;
import org.systempro.project.sdf.TestScreen;

public class Main extends Game {

	@Override
	public void create () {
		setScreen(new ShadowTest()) ;
	}

}
