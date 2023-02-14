package org.systempro.project.basics3d;

import com.badlogic.gdx.graphics.Color;

public class Environment {
    public Light[] lights;
    public Color ambientColor;
    public int lightCount;
    public int maxLights;

    public Environment(){
        ambientColor=new Color(0.1f,0.1f,0.1f,1.0f);
        maxLights=10;
        lights=new Light[maxLights];
        lightCount=0;
    }

    public void add(Light light){
        lights[lightCount]=light;
        lightCount++;
    }
}
