package org.systempro.project.camera;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Camera2d {
    public Vector3 position;
    public float rotation;
    public Vector3 scale;
    public Vector2 size;
    public Matrix3 combined,helper3;
    public Matrix4 combined4,helper4;

    public Camera2d(){
        position=new Vector3(0,0,0);
        scale=new Vector3(1,1,1);
        size=new Vector2(1,1);
        rotation=0;
        combined=new Matrix3().idt();
        helper3=new Matrix3().idt();
        combined4=new Matrix4().idt();
        helper4=new Matrix4().idt();
    }

    public void setTranslation(float x,float y) {
        position.set(x,y,0);
    }

    public void setRotateRad(float radians) {
        rotation=radians;
    }
    public void setRotate(float degrees) {
        rotation=degrees;
    }

    public void setScale(float x,float y) {
        scale.set(1f/x,1f/y,1f);
    }

    public void setSize(float width, float height) {
        size.set(2f/width,2f/height);
    }

    public void update(){
        combined.idt()
            .mul(helper3.setToRotation(rotation))
            .mul(helper3.setToScaling(scale.x,scale.y))
            .mul(helper3.setToScaling(size))
            .mul(helper3.setToTranslation(position.x,position.y));
        combined4.idt()
            .mul(helper4.setToRotation(0,0,1,rotation))
            .mul(helper4.setToScaling(scale))
            .mul(helper4.setToScaling(size.x,size.y,1))
            .mul(helper4.setToTranslation(position));
    }
}
