package org.systempro.project.test3d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class CameraController  {
    float verticalAngle=0,horizontalAngle=0;
    Camera camera;
    public CameraController(Camera camera){
        this.camera=camera;
    }
    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))camera.position.y-=1f/100f;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))camera.position.y+=1f/100f;
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            float dx=Gdx.input.getDeltaX();
            float dy=-Gdx.input.getDeltaY();
            horizontalAngle-=dx/100f;
            verticalAngle  -=dy/100f;
            float x=(float)Math.cos(horizontalAngle)+camera.position.x;
            float y=(float)Math.sin(verticalAngle)  +camera.position.y;
            float z=(float)Math.sin(horizontalAngle)+camera.position.z;
            camera.lookAt(x,y,z);
            camera.up.set(0,1,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            float x=(float) Math.cos(horizontalAngle)/100;
            float z=(float) Math.sin(horizontalAngle)/100;
            camera.position.add(x,0,z);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            float x=(float) Math.cos(horizontalAngle)/100;
            float z=(float) Math.sin(horizontalAngle)/100;
            camera.position.add(-x,0,-z);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            float x=(float) Math.cos(horizontalAngle+Math.PI/2)/100;
            float z=(float) Math.sin(horizontalAngle+Math.PI/2)/100;
            camera.position.add(-x,0,-z);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            float x=(float) Math.cos(horizontalAngle-Math.PI/2)/100;
            float z=(float) Math.sin(horizontalAngle-Math.PI/2)/100;
            camera.position.add(-x,0,-z);
        }

        camera.update();
    }
}
