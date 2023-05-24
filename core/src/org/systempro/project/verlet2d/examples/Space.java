package org.systempro.project.verlet2d.examples;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.scalaui.Fonts;
import org.systempro.project.scalaui.Key;
import org.systempro.project.scalaui.Scene;
import org.systempro.project.scalaui.widgets.Text;
import org.systempro.project.verlet2d.Particle;
import org.systempro.project.verlet2d.Simultaion;

public class Space extends BasicScreen {
    Scene scene;
    Key<Text>[] keys;
    Simultaion simultaion;

    float startX,startY;
    boolean paused=true,down=false;

    @Override
    public void show() {
        simultaion=new Simultaion(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        InfoUI.load();
        scene=InfoUI.scene();
        keys=InfoUI.keys();
        scene.layout();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)&& !down){
//            simultaion.add(new Particle(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY(),5,0.9f,20));
            startX=Gdx.input.getX();
            startY=Gdx.graphics.getHeight()-Gdx.input.getY();
            down=true;
        }
        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)&& down){
            float x=Gdx.input.getX();
            float y=Gdx.graphics.getHeight()-Gdx.input.getY();
            Particle p=new Particle(x,y,5,0.9f,20);
            p.prevPosition.set(x-(x-startX)/1000,y-(y-startY)/1000);
            simultaion.add(p);
            down=false;
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            for(int i=-5;i<=5;i++){
                for(int j=-5;j<=5;j++){
                    simultaion.add(new Particle(Gdx.input.getX()+i*10,Gdx.graphics.getHeight()-Gdx.input.getY()+j*10,5,0.9f,20));
                }
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))paused=!paused;

        long start=System.currentTimeMillis();
        if(!paused)simultaion.update(0.016f,8);
        long physics=System.currentTimeMillis();
        simultaion.draw();
        long draw=System.currentTimeMillis();

        keys[0].widget().text_$eq("physics: "+(physics-start)+"ms");
        keys[1].widget().text_$eq("draw: "+(draw-physics)+"ms");
        keys[2].widget().text_$eq("particles: "+simultaion.particles.size());

        scene.layout();
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
}
