package org.systempro.project.basics3d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class CameraController2 extends InputAdapter {

    private float sensitivity=0.5f,speed=2f;
    private boolean invert=true;
    private float yaw=0,pitch=0,roll=0;
    private float x=0,y=0,z=0;
    public Camera camera;
    private final Vector3 direction=new Vector3();
    private final Vector3 leftRight=new Vector3();
    private boolean focused=false;

    public CameraController2(Camera camera){
        this.camera=camera;
    }

    public CameraController2(Camera camera,float sensitivity,float speed,boolean invert){
        this.camera=camera;
        this.sensitivity=sensitivity;
        this.speed=speed;
        this.invert=invert;
    }

    public CameraController2 setRoll(float roll) {
        this.roll = roll;
        return this;
    }
    public CameraController2 setPitch(float pitch) {
        this.pitch=pitch;
        return this;
    }
    public CameraController2 setYaw(float yaw) {
        this.yaw=yaw;
        return this;
    }

    public CameraController2 setPosition(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
        return this;
    }
    public CameraController2 setPosition(Vector3 position){
        this.x=position.x;
        this.y=position.y;
        this.z=position.z;
        return this;
    }

    public float getRoll() {
        return roll;
    }
    public float getYaw() {
        return yaw;
    }
    public float getPitch() {
        return pitch;
    }

    public Vector3 getDirection(){
        return direction;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        focused=true;
        Gdx.input.setCursorCatched(true);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(focused&& keycode== Input.Keys.ESCAPE){
            focused=false;
            Gdx.input.setCursorCatched(false);
            return true;
        }
        return false;
    }

    public void  update(){
        camera.position.set(0,0,0);
        camera.lookAt(0,0,-1);
        camera.up.set(0,1,0);
        camera.update();
        Quaternion rotation=new Quaternion();
        rotation.setEulerAnglesRad(yaw,pitch,roll);
        camera.rotate(rotation);
        camera.update();
        camera.position.set(x,y,z);
        camera.update();
    }

    public void update(float delta){
        if(focused) {
            float dx = Gdx.input.getDeltaX();
            float dy = Gdx.input.getDeltaY();
            if(invert){
                dx=-dx;
                dy=-dy;
            }
            yaw += dx *delta*sensitivity;
            pitch += dy *delta*sensitivity;

            if(yaw<0)yaw=(float) Math.PI*2;
            if(yaw>((float) (Math.PI*2)))yaw=0;
            if(pitch>Math.PI/2)pitch=(float) Math.PI/2;
            if(pitch<-Math.PI/2)pitch=-(float) Math.PI/2;


            float cos=(float) Math.cos(pitch);

            direction.set(
                (float) Math.sin(yaw),// * (float) Math.cos(pitch),
                (float) Math.sin(pitch),
                (float) Math.cos(yaw) // * (float) Math.cos(pitch)
            );
            leftRight.set(
                (float) Math.sin(yaw+Math.PI/2),// * (float) Math.cos(pitch),
                (float) Math.sin(pitch),
                (float) Math.cos(yaw+Math.PI/2) // * (float) Math.cos(pitch)
            );

            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                x+=direction.x*delta*speed;
                z+=direction.z*delta*speed;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                x-=direction.x*delta*speed;
                z-=direction.z*delta*speed;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                x-=leftRight.x*delta*speed;
                z-=leftRight.z*delta*speed;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                x+=leftRight.x*delta*speed;
                z+=leftRight.z*delta*speed;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                y+=delta*speed;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                y-=delta*speed;
            }
            direction.x*=cos;
            direction.z*=cos;
            leftRight.x*=cos;
            leftRight.z*=cos;
        }
        update();
    }
}
