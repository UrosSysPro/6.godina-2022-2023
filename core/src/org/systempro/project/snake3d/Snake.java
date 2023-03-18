package org.systempro.project.snake3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.systempro.project.basics3d.InstanceRenderer;
import org.systempro.project.basics3d.MeshInstance;

import java.util.ArrayList;

public class Snake {
    private ArrayList<SnakePart> parts;
    private int smerX,smerY;
    public InstanceRenderer renderer;

    int width=50;
    int height=50;

    public Snake(){
        renderer=InstanceRenderer.createInstanceRenderer();
        parts=new ArrayList<>();
        add(0,0);
        for(int i=0;i<5;i++)add();
        smerX=0;
        smerY=0;
    }
    public void add(int x,int y){
        parts.add(new SnakePart(x,y));
    }
    public void add(){
        SnakePart last=parts.get(parts.size()-1);
        parts.add(new SnakePart(last.x,last.y));
    }
    public void input(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            smerY=0;
            smerX=-1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            smerY=0;
            smerX=1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            smerY=1;
            smerX=0;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            smerY=-1;
            smerX=0;
        }
    }
    public void update(){
        for(int i=parts.size()-1;i>=1;i--){
            parts.get(i).x=parts.get(i-1).x;
            parts.get(i).y=parts.get(i-1).y;
        }
        SnakePart first=parts.get(0);
        first.x+=smerX;
        first.y+=smerY;
        if(first.x>=width)first.x=0;
        if(first.y>=height)first.y=0;
        if(first.x<0)first.x=width-1;
        if(first.y<0)first.y=height-1;
    }
    public void draw(int counter,int maxFrames){
        MeshInstance instance=new MeshInstance();
        float a=counter;
        a/=maxFrames;
        for(int i=1;i<parts.size();i++){
            SnakePart p1=parts.get(i);
            SnakePart p2=parts.get(i-1);
            instance.position.set(
                p1.x*(1-a)+p2.x*a,
                p1.y*(1-a)+p2.y*a,
                0
            );
            instance.update();
            renderer.draw(instance);
        }
        SnakePart first=parts.get(0);
        instance.position.set(
            first.x*(1-a)+(first.x+smerX)*a,
            first.y*(1-a)+(first.y+smerY)*a,
            0);
        instance.update();
        renderer.draw(instance);
        renderer.flush();
    }
}
