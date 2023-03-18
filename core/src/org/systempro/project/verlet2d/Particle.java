package org.systempro.project.verlet2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Particle {
    public final Color color=new Color(Color.WHITE);
    public final Vector2 position=new Vector2();
    public final Vector2 prevPosition=new Vector2();
    public final Vector2 acceleration=new Vector2();
    public float mass;
    public float radius;
    public float restitution;

    public Particle(float x,float y,float radius,float restitution,float mass){
        position.set(x,y);
        prevPosition.set(x,y);
        this.radius=radius;
        this.mass=mass;
        this.restitution=restitution;
    }
    public Particle(float x,float y,float radius){
        this(x,y,radius,0.5f,1f);
    }
    public void update(float delta){
        Vector2 tmp=new Vector2(position);
        position.set(
            2*position.x-prevPosition.x+acceleration.x*delta*delta,
            2*position.y-prevPosition.y+acceleration.y*delta*delta
        );
        acceleration.set(0,0);
        prevPosition.set(tmp);
    }
    public void update(float oldDelta,float delta){
        Vector2 tmp=new Vector2(position);
        position.set(
            position.x+(position.x-prevPosition.x)*delta/oldDelta+acceleration.x*(delta+oldDelta)/2*delta,
            position.y+(position.y-prevPosition.y)*delta/oldDelta+acceleration.y*(delta+oldDelta)/2*delta
        );
        acceleration.set(0,0);
        prevPosition.set(tmp);
    }

    public void draw(ShapeRenderer renderer){
        renderer.setColor(color);
        renderer.circle(position.x,position.y,radius);
    }

    public static void resolveCollision(Particle p1,Particle p2){
        float minDistance=p1.radius+p2.radius;
        float distance=distance(p1.position,p2.position);
        if(distance<minDistance){
            Vector2 v1=new Vector2(p1.position).sub(p1.prevPosition);
            Vector2 v2=new Vector2(p2.position).sub(p2.prevPosition);
            Vector2 vrel=new Vector2(v1).sub(v2);
            Vector2 normal=new Vector2(p2.position).sub(p1.position).nor();
            float c=Math.min(p1.restitution,p2.restitution);
            float impulseMagnitude=-(1+c)*normal.dot(vrel)/(1f/p1.mass+1f/p2.mass);


            Vector2 diff=new Vector2(p1.position).sub(p2.position).nor();
            float delta=(minDistance-distance)/2;
            diff.x*=delta;
            diff.y*=delta;
            p1.position.add(diff);
            p2.position.sub(diff);

            v1.add(
                normal.x*impulseMagnitude/p1.mass,
                normal.y*impulseMagnitude/p1.mass
            );
            v2.sub(
                normal.x*impulseMagnitude/p2.mass,
                normal.y*impulseMagnitude/p2.mass
            );

            p1.prevPosition.set(p1.position).sub(v1);
            p2.prevPosition.set(p2.position).sub(v2);
        }
    }

    public static float distance(Vector2 v1,Vector2 v2){
        float dx=v1.x-v2.x;
        float dy=v1.y-v2.y;
        return (float) Math.sqrt(dx*dx+dy*dy);
    }


//    public static void resolveCollision(Particle p1,Particle p2){
//        float minDistance=p1.radius+p2.radius;
//        float distance=distance(p1.position,p2.position);
//        if(distance<minDistance){
//            Vector2 diff=new Vector2(p1.position).sub(p2.position).nor();
//            float delta=(minDistance-distance)/2;
//            diff.x*=delta;
//            diff.y*=delta;
//            p1.position.add(diff);
//            p2.position.sub(diff);
//        }
//    }
}
