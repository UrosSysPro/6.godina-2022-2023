package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.scalaui.UIBench;
import org.systempro.project.scalaui.snake.WidgetsSnake;
import org.systempro.project.test3d.MengerSpongeTest;
import org.systempro.project.verlet2d.MeshSimulation;
import org.systempro.project.verlet2d.TestScreen;
import org.systempro.project.verlet2d.examples.Blocks;
import org.systempro.project.verlet2d.examples.Net;
import org.systempro.project.verlet2d.examples.Pool;
import org.systempro.project.verlet2d.examples.Rope;

public class Main extends Game {

	@Override
	public void create () {
		setScreen( new Pool());
	}

}
