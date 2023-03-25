package org.systempro.project.snake3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import org.systempro.project.basics3d.InstanceRenderer;
import org.systempro.project.basics3d.MeshInstance;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<SnakePart> parts;
    private int smerX,smerY;
    public InstanceRenderer renderer;


    int width=50;
    int height=50;

    public boolean gameOver=false;

    public Snake(){
        Mesh mesh=new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj")).meshes.first();
        MeshInstance.enableInstancing(mesh,1000);
        Texture texture=new Texture("test3d/texture.png");
        renderer=new InstanceRenderer(mesh,texture);
        renderer.camera.position.set(0,0,10);
        renderer.camera.update();
        renderer.camera.lookAt(0,0,0);
        renderer.camera.update();
        parts=new ArrayList<>();
        add(0,0);
        for(int i=0;i<100;i++)add();
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
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&&smerX==0){
            smerY=0;
            smerX=-1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&&smerX==0){
            smerY=0;
            smerX=1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)&&smerY==0){
            smerY=1;
            smerX=0;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)&&smerY==0){
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

        for(SnakePart p:parts){
            if(p==first)continue;
            if(first.x==p.x&&first.y==p.y){
                gameOver=true;
            }
        }
        if(gameOver){
            gameOver=false;
            parts.clear();
            add(0,0);
            for(int i=0;i<100;i++)add();
        }
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
        float x=first.x*(1-a)+(first.x+smerX)*a;
        float y=first.y*(1-a)+(first.y+smerY)*a;
        renderer.camera.position.set(25,25,50);
        renderer.camera.update();
        instance.position.set(x,y,0);
        instance.update();
        renderer.draw(instance);
        renderer.flush();
    }
}
