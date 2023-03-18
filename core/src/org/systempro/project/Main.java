package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.snake3d.TestScreen;
import org.systempro.project.verlet2d.MeshSimulation;

public class Main extends Game {

	@Override
	public void create () {
		setScreen( new MeshSimulation());
	}

}
