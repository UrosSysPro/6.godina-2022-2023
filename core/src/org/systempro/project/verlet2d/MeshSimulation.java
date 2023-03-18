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
        simultaion=new Simultaion(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            createBox(simultaion,3,1.3f,10,Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))paused=!paused;

        if(!paused)simultaion.update(delta,8);
        simultaion.draw();
    }

    public void createBox(Simultaion simultaion,float r,float d,int n,float offsetX,float offsetY){
        Particle[][] particles=new Particle[n][n];
        for(int i=0;i<particles.length;i++){
            for(int j=0;j<particles[i].length;j++){
                float x=offsetX+i*r*2*d;
                float y=offsetY+j*r*2*d;
                Particle p=new Particle(x,y,r,0.5f,1);
                simultaion.particles.add(p);
                particles[i][j]=p;
            }
        }

        for(int i=0;i<particles.length;i++){
            for(int j=0;j<particles[i].length;j++){
                Stick stick;
                float stiffness=0.5f;
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
    public void hide() {
        simultaion.dispose();
    }
}
