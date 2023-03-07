package org.systempro.project.verlet2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

import java.util.ArrayList;
import java.util.Vector;

public class TestScreen extends BasicScreen {

    public Simultaion simultaion;
    public boolean paused=false;

    @Override
    public void show() {
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

        if(!paused)
            simultaion.update(0.016f);
        simultaion.draw();
    }

}
