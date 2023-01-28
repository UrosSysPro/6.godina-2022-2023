package org.systempro.project.platformer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import org.systempro.project.physics2d.Collider;
import org.systempro.project.physics2d.PlazmaBody;

public class Player implements Collider {
    public PlazmaBody hitbox;
    public boolean keyUp,keyDown,keyLeft,keyRight,onGround,wallJumbingLeft,wallJumbingRight;


    public Player(PlazmaBody hitbox){
        for(Fixture fixture:hitbox.body.getFixtureList()){
            fixture.setRestitution(0);
        }
        this.hitbox=hitbox;
        keyDown=false;
        keyRight=false;
        keyUp=false;
        keyLeft=false;
        onGround=true;
        wallJumbingLeft=false;
        wallJumbingRight=false;
        hitbox.fixtureBottom.setUserData(this);
        hitbox.fixtureTop.setUserData(this);
        hitbox.fixtureCenter.setUserData(this);
        hitbox.sensorBottom.setUserData(this);
        hitbox.sensorRight.setUserData(this);
        hitbox.sensorLeft.setUserData(this);
    }

    public Player(World world,float x,float y,float width,float height){
        this(new PlazmaBody(world, x, y, width, height));
    }
    public void update(float delta){
        Vector2 speed = hitbox.getVelocity();
        if (keyLeft) speed.x = -4;
        if (keyRight) speed.x = 4;
        if(onGround)speed.x-=speed.x*0.1;
        if (keyUp && onGround) speed.y = 6;
        if(speed.y<0)speed.y-=10*delta;
        if(wallJumbingRight&&speed.x>0)speed.x=0;
        if(wallJumbingLeft&&speed.x<0)speed.x=0;
        hitbox.setVelocity(speed);
    }
    public void draw(ShapeRenderer renderer){
        hitbox.debugDraw(renderer);
    }

    @Override
    public void beginContact(Fixture fix1, Fixture fix2) {
        if(fix1==hitbox.sensorBottom&&fix2.getUserData() instanceof Platform){
            onGround=true;
            System.out.println("on ground");
        }
        if(fix1== hitbox.sensorLeft&& fix2.getUserData() instanceof Platform){
            wallJumbingLeft=true;
            System.out.println("wall jump start");
        }
        if(fix1== hitbox.sensorRight&& fix2.getUserData() instanceof Platform){
            wallJumbingRight=true;
            System.out.println("wall jump start");
        }
    }

    @Override
    public void endContact(Fixture fix1, Fixture fix2) {
        if(fix1==hitbox.sensorBottom&&fix2.getUserData() instanceof Platform){
            onGround=false;
            System.out.println("in air");
        }
        if(fix1== hitbox.sensorLeft&& fix2.getUserData() instanceof Platform){
            wallJumbingLeft=false;
            System.out.println("wall jump end");
        }
        if(fix1== hitbox.sensorRight&& fix2.getUserData() instanceof Platform){
            wallJumbingRight=false;
            System.out.println("wall jump end");
        }
    }
}
