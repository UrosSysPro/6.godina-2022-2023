package org.systempro.project.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import org.systempro.project.camera.Camera2d;

public class Scene {
    private WidgetRenderer renderer;
    private Camera2d camera2d;
    private Widget root;
    public Scene(Widget root){
        this.root=root;
        renderer=new WidgetRenderer();
        camera2d=new Camera2d();
        float width= Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        resize(width,height);
        renderer.camera2d=camera2d;
    }
    public void layout(){
        float width=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        root.calculateSize(new Size(width,height));
        root.calculateLocation(new Vector2(0,0));
    }
    public void draw(){
        root.draw(renderer);
        renderer.flush();
    }

    public void resize(float width,float height){
        camera2d.setSize(width,height);
        camera2d.setTranslation(-width/2,-height/2);
//        camera2d.setTranslation(0,0);
        camera2d.setScale(1,-1);
        camera2d.update();
    }
}
