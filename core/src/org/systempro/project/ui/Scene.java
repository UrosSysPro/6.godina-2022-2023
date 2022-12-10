package org.systempro.project.ui;

import com.badlogic.gdx.Gdx;
import org.systempro.project.camera.Camera2d;

public class Scene {
    WidgetRenderer renderer;
    Camera2d camera2d;
    public Scene(){
        renderer=new WidgetRenderer();
        camera2d=new Camera2d();
        float width= Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        camera2d.setSize(width,height);
        camera2d.setTranslation(-width/2,-height/2);
//        camera2d.setTranslation(0,0);
        camera2d.setScale(1,-1);
        camera2d.update();
        renderer.camera2d=camera2d;
    }
    public void layout(){

    }
    public void draw(){
        renderer.draw(100,100,200,100);
        renderer.flush();
    }
}
