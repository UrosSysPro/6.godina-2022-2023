package org.systempro.project.basics3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Light {
    public int type;
    public Vector3 position;
    public Vector3 attenuation;
    public Vector3 direction;
    public Color color;

    public Light(){
        type=0;
        position=new Vector3(0,0,0);
        attenuation=new Vector3(0,0,1);
        direction=new Vector3(1,0,0);
        color=new Color(1,1,1,1);
    }
    public Light(int type,Vector3 position,Vector3 attenuation,Vector3 direction,Color color){
        this.type=type;
        this.position=position;
        this.attenuation=attenuation;
        this.direction=direction;
        this.color=color;
    }
}
