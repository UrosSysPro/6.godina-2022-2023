package org.systempro.project.test3d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class CameraController  {
    float verticalAngle=0,horizontalAngle=0;
    Vector3 forwardDir,rightDir;
    float speed=0.02f;

    Camera camera;
    public CameraController(Camera camera){
        this.camera=camera;
        forwardDir=new Vector3();
        rightDir=new Vector3();
        setDirection(verticalAngle,horizontalAngle);
    }

    public void setDirection(float horizontalAngle,float verticalAngle){
        this.horizontalAngle=horizontalAngle;
        this.verticalAngle=verticalAngle;
        if(this.verticalAngle>Math.PI/2)this.verticalAngle=(float) Math.PI/2;
        if(this.verticalAngle<-Math.PI/2)this.verticalAngle=(float) -Math.PI/2;
        float x=(float)Math.cos(horizontalAngle)+camera.position.x;
        float y=(float)Math.sin(verticalAngle)  +camera.position.y;
        float z=(float)Math.sin(horizontalAngle)+camera.position.z;
        camera.lookAt(x,y,z);
        camera.up.set(0,1,0);

        x=(float)Math.cos(horizontalAngle);
        z=(float)Math.sin(horizontalAngle);
        forwardDir.set(x,0,z);

        x=(float) Math.cos(horizontalAngle+Math.PI/2);
        z=(float) Math.sin(horizontalAngle+Math.PI/2);
        rightDir.set(x,0,z);
    }
    public void update(){
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float dx = Gdx.input.getDeltaX();
            float dy = -Gdx.input.getDeltaY();
            float sensitivity = 1f / Math.max(camera.viewportHeight, camera.viewportWidth);
            horizontalAngle -= dx * sensitivity * Math.PI;
            verticalAngle -= dy * sensitivity * Math.PI;
            setDirection(horizontalAngle, verticalAngle);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))camera.position.y -= speed;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))     camera.position.y += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.W))camera.position.add( forwardDir.x*speed,0, forwardDir.z*speed);
        if(Gdx.input.isKeyPressed(Input.Keys.S))camera.position.add(-forwardDir.x*speed,0,-forwardDir.z*speed);
        if(Gdx.input.isKeyPressed(Input.Keys.A))camera.position.add(-rightDir.x  *speed,0,-rightDir.z  *speed);
        if(Gdx.input.isKeyPressed(Input.Keys.D))camera.position.add( rightDir.x  *speed,0, rightDir.z  *speed);
        camera.update();
    }
}
