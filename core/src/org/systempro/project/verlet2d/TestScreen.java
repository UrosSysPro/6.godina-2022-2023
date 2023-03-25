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
    public boolean paused=true;
    public Scene scene;

    @Override
    public void show() {
        TestScreenUI.init();
        scene=TestScreenUI.scene();
        simultaion=new Simultaion(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))paused=!paused;

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            createBox(simultaion,1,5,1,10,Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
        }

        long start=System.currentTimeMillis();
        if(!paused)simultaion.update(delta,8);
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

    public void createBox(Simultaion simultaion,float mass,float r,float d,int n,float offsetX,float offsetY){
        Particle[][] particles=new Particle[n][n];
        for(int i=0;i<particles.length;i++){
            for(int j=0;j<particles[i].length;j++){
                float x=offsetX+i*r*2*d;
                float y=offsetY+j*r*2*d;
                Particle p=new Particle(x,y,r,0.5f,mass);
                simultaion.particles.add(p);
                particles[i][j]=p;
            }
        }

        for(int i=0;i<particles.length;i++){
            for(int j=0;j<particles[i].length;j++){
                Stick stick;
                float stiffness=1f;
                if(i+1<particles.length&&j+1<particles[i].length){
                    stick=new Stick(particles[i][j],particles[i+1][j+1],r*(float) Math.sqrt(2)*d*2,stiffness);
                    simultaion.sticks.add(stick);
                    stick=new Stick(particles[i+1][j],particles[i][j+1],r*(float) Math.sqrt(2)*d*2,stiffness);
                    simultaion.sticks.add(stick);
                }
                if(j+1<particles[i].length){
                    stick=new Stick(particles[i][j],particles[i][j+1],r*d*2,stiffness);
                    simultaion.sticks.add(stick);
                }
                if(i+1<particles.length){
                    stick=new Stick(particles[i][j],particles[i+1][j],r*d*2,stiffness);
                    simultaion.sticks.add(stick);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        simultaion.resize(width,height,20);
    }
    @Override
    public void hide() {
        simultaion.dispose();
    }
}
