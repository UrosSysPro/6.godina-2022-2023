package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.platformer.TestScreen;
import org.systempro.project.renderers.ShadowTest;
import org.systempro.project.scalaui.snake.WidgetsSnake;

public class Main extends Game {

	@Override
	public void create () {
		setScreen(new TestScreen()) ;
	}

}
