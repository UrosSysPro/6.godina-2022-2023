package org.systempro.project.basics3d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class CameraController extends InputAdapter {
    public float verticalAngle=0,horizontalAngle=0;
    public Vector3 forwardDir,rightDir;
    public float speed=0.1f*1000/60;

    public Camera camera;
    public boolean focused;
    public boolean invertedCamera=false;
    public CameraController(Camera camera){
        focused=false;
        this.camera=camera;
        forwardDir=new Vector3();
        rightDir=new Vector3();
        setDirection(verticalAngle,horizontalAngle);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        focused=true;
        Gdx.input.setCursorCatched(true);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(focused && keycode==Input.Keys.ESCAPE){
            focused=false;
            Gdx.input.setCursorCatched(false);
        }
        return false;
    }

    public void setDirection(float horizontalAngle, float verticalAngle){
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
    public void update(float delta){
        if(!focused)return;
        float dx = Gdx.input.getDeltaX();
        float dy = -Gdx.input.getDeltaY();
        if(invertedCamera){
            dx=-dx;
            dy=-dy;
        }
        float sensitivity = 1f / Math.max(camera.viewportHeight, camera.viewportWidth);
        horizontalAngle -= dx * sensitivity * Math.PI*delta*100;
        verticalAngle -= dy * sensitivity * Math.PI*delta*100;
        setDirection(horizontalAngle, verticalAngle);
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))camera.position.y -= speed*delta;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))     camera.position.y += speed*delta;
        if(Gdx.input.isKeyPressed(Input.Keys.W))camera.position.add( forwardDir.x*speed*delta,0, forwardDir.z*speed*delta);
        if(Gdx.input.isKeyPressed(Input.Keys.S))camera.position.add(-forwardDir.x*speed*delta,0,-forwardDir.z*speed*delta);
        if(Gdx.input.isKeyPressed(Input.Keys.A))camera.position.add(-rightDir.x  *speed*delta,0,-rightDir.z  *speed*delta);
        if(Gdx.input.isKeyPressed(Input.Keys.D))camera.position.add( rightDir.x  *speed*delta,0, rightDir.z  *speed*delta);
        if(Gdx.input.isKeyPressed(Input.Keys.E) && camera instanceof PerspectiveCamera){
            PerspectiveCamera camera1 = (PerspectiveCamera) camera;
            camera1.fieldOfView+=0.5f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q) && camera instanceof PerspectiveCamera){
            PerspectiveCamera camera1 = (PerspectiveCamera) camera;
            camera1.fieldOfView-=0.5f;
        }
        camera.update();
    }
}
