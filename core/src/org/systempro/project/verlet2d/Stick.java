package org.systempro.project.verlet2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Stick {
    public Particle particle1,particle2;
    public float length;
    public float stiffness;
    public Stick(Particle particle1,Particle particle2,float length,float stiffness){
        this.particle1=particle1;
        this.particle2=particle2;
        this.length=length;
        this.stiffness=stiffness;
    }
    public Stick(Particle particle1,Particle particle2,float length){
        this(particle1,particle2,length,1);
    }

    public void apply(){
        Vector2 diff=new Vector2(particle1.position).sub(particle2.position);

        float diffLength=diff.len();

        float diffFactor=(length-diffLength)/diffLength;
        Vector2 offset=new Vector2(
          diff.x*diffFactor*stiffness,
          diff.y*diffFactor*stiffness
        );
        particle1.position.add(
            offset.x*particle1.invMass/(particle1.invMass+ particle2.invMass),
            offset.y*particle1.invMass/(particle1.invMass+ particle2.invMass)
        );
        particle2.position.sub(
            offset.x*particle2.invMass/(particle1.invMass+ particle2.invMass),
            offset.y*particle2.invMass/(particle1.invMass+ particle2.invMass)
        );
    }

    public void draw(ShapeRenderer renderer){
        renderer.setColor(Color.LIME);
        renderer.rectLine(particle1.position,particle2.position,2);
    }
}
