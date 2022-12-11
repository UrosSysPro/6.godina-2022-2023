package org.systempro.project.ui;

public class Size {
    public float width,height;
    public Size(float width,float height){
        this.width=width;
        this.height=height;
    }
    public Size set(Size size){
        width=size.width;
        height=size.height;
        return this;
    }
    public Size set(float width,float height){
        this.width=width;
        this.height=height;
        return this;
    }
}
