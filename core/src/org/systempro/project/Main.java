package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.coolShaders.JuliaSet;
import org.systempro.project.coolShaders.MandelbrotSet;
import org.systempro.project.scalaui.snake.WidgetsSnake;
import org.systempro.project.shadowMapTest.TestScreen;
import org.systempro.project.test3d.MengerSpongeTest;
import org.systempro.project.verlet2d.Box2DTest;
import org.systempro.project.verlet2d.MeshSimulation;

public class Main extends Game {

	@Override
	public void create () {
		setScreen( new TestScreen()) ;
	}

}
