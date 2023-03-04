package org.systempro.project.verlet2d;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class Simultaion {

    public ShapeRenderer renderer;
    public Random random;

    public ArrayList<Particle> particles;
    public ArrayList<Particle> fixedParticles;
    public ArrayList<Stick> sticks;
    public float width=800,height=600;

    public Simultaion(){
        random=new Random();
        renderer=new ShapeRenderer();
        particles=new ArrayList<>();
        fixedParticles=new ArrayList<>();
        sticks=new ArrayList<>();
    }
    public void add(float x,float y){
        particles.add(new Particle(x,y,x,y,1));
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
        sticks.add(new Stick(particles[1],particles[2],size*(float) Math.sqrt(2)));
    }
    public void update(float delta){
        updateParticles(delta);
        stickConstraints();
        boxConstraint();
    }

    public void updateParticles(float delta){
        Vector2 force=new Vector2(0,-300f);
        for(Particle particle:particles){
            particle.update(delta,force);
        }
    }
    public void boxConstraint(){
        for(Particle particle:particles){
            if(particle.position.y<0){
                float vy=particle.position.y-particle.prevPosition.y;
                particle.position.y=0;
                particle.prevPosition.y=vy*particle.restitution;
            }
            if(particle.position.y>height){
                float vy=particle.position.y-particle.prevPosition.y;
                particle.position.y=height;
                particle.prevPosition.y=height+vy*particle.restitution;
            }
            if(particle.position.x<0){
                float vx=particle.position.x-particle.prevPosition.x;
                particle.position.x=0;
                particle.prevPosition.x=vx*particle.restitution;
            }
            if(particle.position.x>width){
                float vx=particle.position.x-particle.prevPosition.x;
                particle.position.x=width;
                particle.prevPosition.x=width+vx*particle.restitution;
            }
        }
    }

    public void stickConstraints(){
        for(Stick stick:sticks){
            stick.apply();
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
        for(Particle particle:fixedParticles){
            particle.draw(renderer);
        }
    }
    public void drawSticks(){
        for(Stick stick:sticks){
            stick.draw(renderer);
        }
    }
}
