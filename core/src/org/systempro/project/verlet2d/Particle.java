package org.systempro.project.verlet2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Particle {
    public final Vector2 position=new Vector2();
    public final Vector2 prevPosition=new Vector2();
    public float mass=1;

    public float radius=5;
    public float restitution=0.9f;

    public Particle(float x,float y,float prevX,float prevY,float mass){
        position.set(x,y);
        prevPosition.set(prevX,prevY);
        this.mass=mass;
    }
    public Particle(Vector2 position,Vector2 prevPosition,float mass){
        this.position.set(position);
        this.prevPosition.set(prevPosition);
        this.mass=mass;
    }
    public void update(float delta,Vector2 force){
        Vector2 acceleration=new Vector2(
            force.x/mass,
            force.y/mass
        );

        Vector2 tmp=new Vector2(position);
        position.set(
          2*position.x-prevPosition.x+acceleration.x*delta*delta,
          2*position.y-prevPosition.y+acceleration.y*delta*delta
        );
        prevPosition.set(tmp);
    }

    public void draw(ShapeRenderer renderer){
        renderer.setColor(Color.WHITE);
        renderer.circle(position.x,position.y,radius);
    }
}
