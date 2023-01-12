package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.scalaui.NewRendererTest;
import org.systempro.project.scalaui.TestScreen;
import org.systempro.project.scalaui.UIBench;

public class Main extends Game {

	@Override
	public void create () {
		setScreen(new TestScreen());
	}

}
