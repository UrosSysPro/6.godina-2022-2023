package org.systempro.project.verlet2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Particle {
    public final Color color=new Color(Color.WHITE);
    public final Vector2 position=new Vector2();
    public final Vector2 prevPosition=new Vector2();
    public final Vector2 acceleration=new Vector2();
    public float mass=1;
    public float radius=3;
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


//    public static void resolveCollision(Particle p1,Particle p2){
//        Vector2 diff=new Vector2(
//            p1.position.x-p2.position.x,
//            p1.position.y-p2.position.y
//        );
//        float distance=diff.len();
//        float minDistance=p1.radius+p2.radius;
//        if(distance<minDistance){
//            float delta=minDistance-distance;
//            float u1=distance(p1.position,p1.prevPosition);
//            float u2=distance(p2.position,p2.prevPosition);
//            float m1=p1.mass;
//            float m2=p2.mass;
//            float v1=(m1*u1+m2*u2+m2*0.0f*(u2-u1))/(m1+m2);
//            float v2=(m1*u1+m2*u2+m1*0.0f*(u1-u2))/(m1+m2);
//            p1.position.add(
//                diff.x/distance*delta/2,
//                diff.y/distance*delta/2
//            );
//            p2.position.sub(
//                diff.x/distance*delta/2,
//                diff.y/distance*delta/2
//            );
//            p1.prevPosition.sub(p1.position);
//            p1.prevPosition.x*=v1;
//            p1.prevPosition.y*=v1;
//            p1.prevPosition.add(p1.position);
//
//            p2.prevPosition.sub(p2.position);
//            p2.prevPosition.x*=v2;
//            p2.prevPosition.y*=v2;
//            p2.prevPosition.add(p2.position);
//        }
//    }
    public static float distance(Vector2 v1,Vector2 v2){
        float dx=v1.x-v2.x;
        float dy=v1.y-v2.y;
        return (float) Math.sqrt(dx*dx+dy*dy);
    }


    public static void resolveCollision(Particle p1,Particle p2){
        Vector2 diff=new Vector2(
            p1.position.x-p2.position.x,
            p1.position.y-p2.position.y
        );
        float distance=diff.len();
        float minDistance=p1.radius+p2.radius;
        if(distance<minDistance){
            float delta=minDistance-distance;
            p1.position.add(
                diff.x/distance*delta/2,
                diff.y/distance*delta/2
            );
            p2.position.sub(
                diff.x/distance*delta/2,
                diff.y/distance*delta/2
            );
        }
    }
}
