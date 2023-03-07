package org.systempro.project.verlet2d;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class Simultaion {

    public ShapeRenderer renderer;
    public Random random;

    public ArrayList<Particle> particles;
    public ArrayList<Stick> sticks;
    public float width=800,height=600;

    public Simultaion(){
        random=new Random();
        renderer=new ShapeRenderer();
        particles=new ArrayList<>();
        sticks=new ArrayList<>();
    }
    public void add(float x,float y){
        for(int i=-2;i<=2;i++){
            for(int j=-2;j<=2;j++){
                float px=x+i*10;
                float py=y+j*10;
                particles.add(new Particle(px,py,px,py,1));
            }
        }

    }
    public void addBox(float x,float y){
        float size=50;
        Particle[] particles=new Particle[4];
        for(int index=0;index<particles.length;index++){
            int i=index%2;
            int j=index/2;
            float px=x+(i==0?size/2:(-size/2));
            float py=y+(j==0?size/2:(-size/2));
            particles[index]=new Particle(px,py,px,py,1);
            this.particles.add(particles[index]);
        }
        sticks.add(new Stick(particles[0],particles[1],size));
        sticks.add(new Stick(particles[0],particles[2],size));
        sticks.add(new Stick(particles[3],particles[1],size));
        sticks.add(new Stick(particles[3],particles[2],size));
        sticks.add(new Stick(particles[0],particles[3],size*(float) Math.sqrt(2)));
//        sticks.add(new Stick(particles[1],particles[2],size*(float) Math.sqrt(2)));
    }
    public void update(float delta){
        int subSteps=8;
        float subDelta=delta/subSteps;
        for(int i=0;i<subSteps;i++){
            stickConstraints();
            collisionConstraint();
            boxConstraint();
            updateInertia(subDelta);
        }
    }

    public void updateInertia(float delta){
        Vector2 gravity=new Vector2(0,-300f);
        for(Particle particle:particles){
            particle.acceleration.add(
                gravity.x/particle.mass,
                gravity.y/particle.mass
            );
            particle.update(delta);
        }
    }
    public void boxConstraint(){
        for(Particle particle:particles){
            float x=particle.position.x;
            float y=particle.position.y;
            float prevX=particle.prevPosition.x;
            float prevY=particle.prevPosition.y;
            float r=particle.radius;
            float restitution=particle.restitution;
            if(y<r){
                float vy=y-prevY;
                y=r;
                prevY=y+vy*restitution;
            }
            if(y>height-r){
                float vy=y-prevY;
                y=height-r;
                prevY=y+vy*restitution;
            }
            if(x<r){
                float vx=x-prevX;
                x=r;
                prevX=x+vx*restitution;
            }
            if(x>width-r){
                float vx=x-prevX;
                x=width-r;
                prevX=x+vx*restitution;
            }
            particle.position.set(x,y);
            particle.prevPosition.set(prevX,prevY);
        }
    }

    public void stickConstraints(){
        for(Stick stick:sticks){
            stick.apply();
        }
    }

    public void collisionConstraint(){
        for(int i=0;i<particles.size();i++){
            Particle p1=particles.get(i);
            for(int j=0;j<particles.size();j++){
                if(i==j)continue;
                Particle p2=particles.get(j);

                resolveCollision(p1,p2);
            }
        }
    }
    public void resolveCollision(Particle p1,Particle p2){
        Vector2 diff=new Vector2(
            p1.position.x-p2.position.x,
            p1.position.y-p2.position.y
        );
        float distance=diff.len();
        float minDistance=p1.radius+p2.radius;
        if(distance<minDistance){
            float delta=minDistance-distance;
            Vector2 v1=new Vector2(
              p1.position.x-p1.prevPosition.x,
              p1.position.y-p1.prevPosition.y
            );
            Vector2 v2=new Vector2(
                p2.position.x-p2.prevPosition.x,
                p2.position.y-p2.prevPosition.y
            );
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

    public void draw(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        drawParticles();
        drawSticks();
        renderer.end();
    }

    public void drawParticles(){
        for(Particle particle:particles){
            particle.draw(renderer);
        }
    }
    public void drawSticks(){
        for(Stick stick:sticks){
            stick.draw(renderer);
        }
    }
}
