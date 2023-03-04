package org.systempro.project.verlet2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Stick {
    public Particle particle1,particle2;
    public float length;
    public Stick(Particle particle1,Particle particle2,float length){
        this.particle1=particle1;
        this.particle2=particle2;
        this.length=length;
    }
    public void apply(){
        Vector2 diff=new Vector2(particle1.position);
        diff.sub(particle2.position);
//        Vector2 diff=new Vector2(particle2.position);
//        diff.sub(particle1.position);
        float diffLength=diff.len();
//        System.out.println(diffLength);

        float diffFactor=(length-diffLength)/diffLength*0.5f;
        Vector2 offset=new Vector2(
          diff.x*diffFactor,
          diff.y*diffFactor
        );
        particle1.position.x+=offset.x;
        particle1.position.y+=offset.y;
        particle2.position.x-=offset.x;
        particle2.position.y-=offset.y;
    }

    public void draw(ShapeRenderer renderer){
        renderer.setColor(Color.LIME);
        renderer.rectLine(particle1.position,particle2.position,2);
    }
}
