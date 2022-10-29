package org.systempro.project.camera;

import com.badlogic.gdx.math.Matrix3;

public class Camera2d {
    private Matrix3 translation,scale,size,rotate;
    public Matrix3 combined;

    public Camera2d(){
        translation=new Matrix3().idt();
        scale=new Matrix3().idt();
        size=new Matrix3().idt();
        rotate=new Matrix3().idt();
        combined=new Matrix3().idt();
    }

    public void setTranslation(float x,float y) {
        translation.setToTranslation(x,y);
    }

    public void setRotateRad(float radians) {
        rotate.setToRotationRad(radians);
    }
    public void setRotate(float degrees) {
        rotate.setToRotation(degrees);
    }

    public void setScale(float scale) {
        this.scale.setToScaling(scale,scale);
    }

    public void setSize(float width, float height) {
        size.setToScaling(2f/width,2f/height);
    }

    public void update(){
        combined.idt().mul(rotate).mul(scale).mul(size).mul(translation);
    }
}
