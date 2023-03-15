package org.systempro.project.verlet2d;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class Simultaion implements Disposable {

    public ShapeRenderer renderer;
    public Random random;

    public ArrayList<Particle> particles;
    public ArrayList<Stick> sticks;
    public float width=800,height=600;
    public float oldDelta=1000f/60f/8f;
    public HashTable table;
    public ExecutorService service;
    private ArrayList<Future> futures;
    private CollisionColumnThread[] threads;

    public Simultaion(){
        random=new Random();
        renderer=new ShapeRenderer();
        particles=new ArrayList<>();
        sticks=new ArrayList<>();
        table=new HashTable((int) width, (int) height,10);
        service=Executors.newFixedThreadPool(16);
        futures=new ArrayList<>();
        threads=new CollisionColumnThread[table.cells.length];
        for(int i=0;i<threads.length;i++){
            threads[i]=new CollisionColumnThread(table.cells, i);
        }
    }
    public void add(float x,float y){
        int count=10;
        for(int i=-count/2;i<=count/2;i++){
            for(int j=-count/2;j<=count/2;j++){
                float px=x+i*6;
                float py=y+j*6;
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
    public void update(float delta,int subSteps){
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
            particle.update(oldDelta,delta);
            oldDelta=delta;
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

    public void collisionConstraintSingleThread(){
        for(int i=0;i<particles.size();i++){
            Particle p1=particles.get(i);
            for(int j=0;j<particles.size();j++){
                if(i==j)continue;
                Particle p2=particles.get(j);

                resolveCollision(p1,p2);
            }
        }
    }

    public void collisionConstraintHashTable(){
        table.removeAll();
        for(Particle p:particles){
            table.insert(p);
        }
        for(int i=0;i<table.cells.length;i++){
            for(int j=0;j<table.cells[i].length;j++){
                for(Particle p:table.cells[i][j]){
                    collideWithNearCells(p,i,j);
                }
            }
        }
    }

    public void collisionConstraint() {
        table.removeAll();
        for(Particle p:particles){
            table.insert(p);
        }
        try{
            for(int offset=0;offset<3;offset++){
                for(int i=offset;i<table.cells.length;i+=3){
                    futures.add(service.submit(threads[i]));
                }
                for(Future f:futures){
                    f.get();
                }
                futures.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void collideWithNearCells(Particle p1,int x,int y){
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if(x+i<0||x+i>=table.cells.length||y+j<0||y+j>=table.cells[0].length)continue;
                for(Particle p2:table.cells[x+i][y+j]){
                    if(p1==p2)continue;
                    resolveCollision(p1,p2);
                }
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

    private void drawParticles(){
        for(Particle particle:particles){
            particle.draw(renderer);
        }
    }
    private void drawSticks(){
        for(Stick stick:sticks){
            stick.draw(renderer);
        }
    }

    @Override
    public void dispose() {
        service.shutdown();
        try {
            if(!service.awaitTermination(1,TimeUnit.MILLISECONDS)){
                System.out.println("Error");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
