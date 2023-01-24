package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.scalaui.TextWidgetTest;
import org.systempro.project.scalaui.snake.WidgetsSnake;
import org.systempro.project.sdf.TestScreen;

public class Main extends Game {

	@Override
	public void create () {
		setScreen(new TestScreen());
	}

}
