package org.systempro.project.verlet2d.examples;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.scalaui.Fonts;
import org.systempro.project.scalaui.Scene;
import org.systempro.project.verlet2d.FixedParticle;
import org.systempro.project.verlet2d.Particle;
import org.systempro.project.verlet2d.Simultaion;
import org.systempro.project.verlet2d.Stick;

public class Rope extends BasicScreen {

    Scene scene;
    Simultaion simultaion;
    boolean paused=true,pressed=false;

    Vector2 start,end;

    @Override
    public void show() {
        simultaion=new Simultaion(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        InfoUI.load();
        scene=InfoUI.scene();
        scene.layout();
        start=new Vector2();
        end=new Vector2();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)&&!pressed){
            start.set(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
            pressed=true;
        }

        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&pressed){
            end.set(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
            createRope(start,end);
            pressed=false;
        }

        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            simultaion.add(new Particle(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY(),5,0.9f,20));
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))paused=!paused;

        if(!paused)simultaion.update(0.016f,8);
        simultaion.draw();

        scene.draw();
    }

    @Override
    public void resize(int width, int height) {
        scene.resize(width,height);
        simultaion.resize(width,height,10);
    }

    @Override
    public void hide() {
        Fonts.dispose();
        simultaion.dispose();
    }
    public void createRope(Vector2 start,Vector2 end){
        float len=new Vector2(start).sub(end).len();
        float r=5;
        int n=(int)(len/(2*r));
        Particle[] particles=new Particle[n];
        Stick[] sticks=new Stick[n-1];

        for(int i=0;i<n;i++){
            float a=i;
            a/=n;
            particles[i]=new Particle(
                start.x*a+end.x*(1-a),
                start.y*a+end.y*(1-a),
                r,0.7f,10
            );
        }

        for(int i=0;i<n-1;i++){
            sticks[i]=new Stick(particles[i],particles[i+1],2*r,1f);
        }


        sticks[0].length*=1.1f;
        sticks[n-2].length*=1.1f;
        particles[0].mass=1000;
        particles[n-1].mass=1000;
        FixedParticle p1=new FixedParticle(particles[0],new Vector2(particles[0].position));
        FixedParticle p2=new FixedParticle(particles[n-1],new Vector2(particles[n-1].position));
        simultaion.add(p1);
        simultaion.add(p2);

        for(Stick s:sticks)simultaion.add(s);
//        for(int i=0;i<n;i++)simultaion.add(particles[i]);
        for(int i=1;i<n-1;i++)simultaion.add(particles[i]);
    }
}
