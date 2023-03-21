package org.systempro.project.verlet2d;

import com.badlogic.gdx.math.Vector2;

public class FixedParticle {
    public Particle particle;
    public Vector2 position;

    public FixedParticle(Particle particle,Vector2 position){
        this.particle=particle;
        this.position=position;
    }
    public FixedParticle(float x,float y,float r){
        particle=new Particle(x,y,r,1,10000);
        position=new Vector2(x,y);
    }
}
