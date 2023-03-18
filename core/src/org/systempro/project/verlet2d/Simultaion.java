package org.systempro.project.verlet2d;

import com.badlogic.gdx.Gdx;
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
    public float width,height;
    public float oldDelta=1f/60f/8f;
    public HashTable table;
    public ExecutorService service;
    private ArrayList<Future> futures;
    private CollisionColumnThread[] threads;

    public Simultaion(float width,float height){
        this.width=width;
        this.height=height;
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
    public void add(Particle p){
        particles.add(p);
    }
    public void add(Stick s){
        sticks.add(s);
    }
    public void update(float delta,int subSteps){
        if(delta>0.05f)delta=0.05f;
        float subDelta=delta/subSteps;
        for(int i=0;i<subSteps;i++){
            stickConstraints();
            collisionConstraintHashTable();
//            collisionConstraint();
//            collisionConstraintMultiColumn();
            boxConstraint();
            applyForces();
            updateInertia(subDelta);
            oldDelta=subDelta;
        }
    }

    public void updateInertia(float delta){
        for(Particle particle:particles){
            particle.update(oldDelta,delta);
        }
    }
    public void applyForces(){
        Vector2 gravity=new Vector2(0,-300f);
        for(Particle particle:particles){
            particle.acceleration.add(
                gravity.x,
                gravity.y
            );
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
//            if(y<r){
//                float vy=y-prevY;
//                y=r;
//                prevY=y+vy;
//            }
//            if(y>height-r){
//                float vy=y-prevY;
//                y=height-r;
//                prevY=y+vy;
//            }
//            if(x<r){
//                float vx=x-prevX;
//                x=r;
//                prevX=x+vx;
//            }
//            if(x>width-r){
//                float vx=x-prevX;
//                x=width-r;
//                prevX=x+vx;
//            }

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

                Particle.resolveCollision(p1,p2);
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

    public void collisionConstraintMultiColumn(){
        table.removeAll();
        for(Particle p:particles){
            table.insert(p);
        }
        try{
            int columns=5;

            for(int offset=0;offset<2;offset++){
                for(int i=offset*columns;i<table.cells.length;i+=columns){
                    int start=i;
                    int end=i+columns;
                    if(i+columns>=table.cells.length)end=table.cells.length-1;
                    futures.add(service.submit(new MultiColumnCollisionThread(table.cells, start,end)));
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
                    Particle.resolveCollision(p1,p2);
                }
            }
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
        service.shutdownNow();
        try {
            if(!service.awaitTermination(1,TimeUnit.MILLISECONDS)){
                System.out.println("Error za brisanje threadova");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
