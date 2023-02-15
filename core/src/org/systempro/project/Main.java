package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.test3d.MengerSpongeTest;

public class Main extends Game {

	@Override
	public void create () {
		setScreen(new MengerSpongeTest()) ;
	}

}
