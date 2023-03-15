package org.systempro.project.verlet2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.scalaui.Scene;

import java.util.ArrayList;
import java.util.Vector;

public class TestScreen extends BasicScreen {

    public Simultaion simultaion;
    public boolean paused=false;
    public Scene scene;

    @Override
    public void show() {
        TestScreenUI.init();
        scene=TestScreenUI.scene();
        simultaion=new Simultaion();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))paused=!paused;

        float x= Gdx.input.getX();
        float y=Gdx.graphics.getHeight()-Gdx.input.getY();
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
            simultaion.add(x,y);

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            for(Particle particle: simultaion.particles){
                particle.acceleration.add(Gdx.input.getDeltaX()*100,-Gdx.input.getDeltaY()*600);
            }
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
            simultaion.addBox(x,y);
        long start=System.currentTimeMillis();
        if(!paused)
            simultaion.update(delta,4);
        long physics=System.currentTimeMillis();
        simultaion.draw();
        long draw=System.currentTimeMillis();

        TestScreenUI.physicsTime().widget().text_$eq("physics update: "+(physics-start));
        TestScreenUI.drawTime().widget().text_$eq("draw: "+(draw-physics));
        TestScreenUI.particles().widget().text_$eq("particles: "+(simultaion.particles.size()));

        scene.layout();
        scene.animate(delta);
        scene.draw();
        long ui=System.currentTimeMillis();
        TestScreenUI.ui().widget().text_$eq("ui: "+(ui-draw));

    }

    @Override
    public void dispose() {
        simultaion.dispose();
    }
}
