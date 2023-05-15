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
public class Net extends BasicScreen {

    Scene scene;
    Simultaion simultaion;
    boolean paused=true,mouseDown=false;

    @Override
    public void show() {
        simultaion=new Simultaion(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        InfoUI.load();
        scene=InfoUI.scene();
        scene.layout();

        createBox(simultaion,1,0.5f,25,20,500,400);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)&& !mouseDown){
//            createBox(simultaion,1,5,1.05f,20,Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
            mouseDown=true;
            System.out.println("down");
        }
        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)&& mouseDown){
//            simultaion.add(new Particle(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY(),5,0.9f,20));
            mouseDown=false;
            System.out.println("up");
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


    public void createBox(Simultaion simultaion,float mass,float r,float d,int n,float offsetX,float offsetY){
        Particle[][] particles=new Particle[n][n];
        for(int i=0;i<particles.length;i++){
            for(int j=0;j<particles[i].length;j++){
                float x=offsetX+i*r*2*d;
                float y=offsetY+j*r*2*d;
                Particle p=new Particle(x,y,3,0.1f,mass);
//                simultaion.particles.add(p);
                particles[i][j]=p;
            }
        }
        FixedParticle p1=new FixedParticle(particles[0][n-1],new Vector2(particles[0][n-1].position));
        FixedParticle p2=new FixedParticle(particles[n-1][n-1],new Vector2(particles[n-1][n-1].position));
        simultaion.add(p1);
        simultaion.add(p2);
        for(int i=1;i<n-1;i++)simultaion.add(particles[i][n-1]);
        for(int i=0;i<n;i++){
            for(int j=0;j<n-1;j++){
                simultaion.add(particles[i][j]);
            }
        }

        for(int i=0;i<particles.length;i++){
            for(int j=0;j<particles[i].length;j++){
                Stick stick;
                float stiffness=0.05f;
//                if(i+1<particles.length&&j+1<particles[i].length){
//                    stick=new Stick(particles[i][j],particles[i+1][j+1],r*(float) Math.sqrt(2)*d*2,stiffness);
//                    simultaion.sticks.add(stick);
//                    stick=new Stick(particles[i+1][j],particles[i][j+1],r*(float) Math.sqrt(2)*d*2,stiffness);
//                    simultaion.sticks.add(stick);
//                }
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
}
