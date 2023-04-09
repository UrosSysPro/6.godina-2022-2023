package org.systempro.project.verlet2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.systempro.project.camera.Camera2d;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class Simultaion implements Disposable {

    public ShapeRenderer renderer;
    public Random random;
    public ArrayList<Particle> particles;
    public ArrayList<FixedParticle> fixedParticles;
    public ArrayList<Stick> sticks;
    public float width,height;
    public float oldDelta=1f/60f/8f;
    public HashTable table;
    public Camera2d camera2d;

    //deo za menjanje
    public ExecutorService service;
    public Future<Void>[] futures;

    public Simultaion(float width,float height){
        this.width=width;
        this.height=height;
        random=new Random();
        renderer=new ShapeRenderer();
        particles=new ArrayList<>();
        fixedParticles=new ArrayList<>();
        sticks=new ArrayList<>();
        table=new HashTable((int) width, (int) height,10);
        camera2d=new Camera2d();
        camera2d.setPosition(width/2,height/2);
        camera2d.setSize(width,height);
        camera2d.update();
        renderer.setProjectionMatrix(camera2d.combined4);
    }
    public void add(Particle p){
        particles.add(p);
    }
    public void add(Stick s){
        sticks.add(s);
    }
    public void add(FixedParticle p){fixedParticles.add(p);}
    public void update(float delta,int subSteps){
        if(delta>0.05f)delta=0.05f;
        float subDelta=delta/subSteps;
        for(int i=0;i<subSteps;i++){
            stickConstraints();
            //neki drugi collision listener
//            collisionConstraintHashTable();
            fixedConstraint();
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
    public void fixedConstraint(){
        for(FixedParticle fixedParticle:fixedParticles){
            Particle particle=fixedParticle.particle;
            particle.position.set(fixedParticle.position);
            particle.prevPosition.set(fixedParticle.position);
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

    public void collisionContraintParallel(){

    }

    public void collisionConstraintNSquared(){
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
        for(FixedParticle fixedParticle:fixedParticles){
            table.insert(fixedParticle.particle);
        }
        for(int i=0;i<table.cells.length;i++){
            for(int j=0;j<table.cells[i].length;j++){
                for(Particle p:table.cells[i][j]){
                    collideWithNearCells(p,i,j);
                }
            }
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
        drawFixedParticles();
        renderer.end();
    }

    private void drawParticles(){
        for(Particle particle:particles){
            particle.draw(renderer);
        }
    }

    private void drawFixedParticles(){
        for(FixedParticle fixedParticle:fixedParticles){
            fixedParticle.particle.draw(renderer);
        }
    }
    private void drawSticks(){
        for(Stick stick:sticks){
            stick.draw(renderer);
        }
    }

    public void resize(int width,int height,int cellSize) {
        this.width=width;
        this.height=height;
        table=new HashTable(width,height,cellSize);
        camera2d.setPosition(width/2,height/2);
        camera2d.setSize(width,height);
        camera2d.update();
        renderer.setProjectionMatrix(camera2d.combined4);
    }

    @Override
    public void dispose() {

    }
}
