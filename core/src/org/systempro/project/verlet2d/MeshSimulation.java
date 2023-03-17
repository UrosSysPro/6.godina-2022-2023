package org.systempro.project.verlet2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;


public class MeshSimulation extends BasicScreen {

    public Simultaion simultaion;
    public boolean paused=true;

    @Override
    public void show() {
        simultaion=new Simultaion();
        simultaion.update(1f/60f,8);
        simultaion.particles.add(new Particle(0,0,0,0,1));
        Particle[][] particles=new Particle[10][10];
        float r=10;
        for(int i=0;i<particles.length;i++){
            for(int j=0;j<particles[i].length;j++){
                float x=100+i*r*2;
                float y=100+j*r*2;
                Particle p=new Particle(x,y,x,y,1);
                simultaion.particles.add(p);
                particles[i][j]=p;
            }
        }

        for(int i=0;i<particles.length-1;i++){
            for(int j=0;j<particles[i].length-1;j++){
                Stick stick;
                stick=new Stick(particles[i][j],particles[i+1][j+1],r*(float) Math.sqrt(2)*2);
                simultaion.sticks.add(stick);
                stick=new Stick(particles[i][j],particles[i][j+1],r*2);
                simultaion.sticks.add(stick);
                stick=new Stick(particles[i][j],particles[i+1][j],r*2);
                simultaion.sticks.add(stick);
            }
        }

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))paused=!paused;

        if(!paused)simultaion.update(delta,8);
        simultaion.draw();
    }

    @Override
    public void hide() {
        simultaion.dispose();
    }
}
