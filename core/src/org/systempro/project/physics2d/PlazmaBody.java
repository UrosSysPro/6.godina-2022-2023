package org.systempro.project.physics2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PlazmaBody extends PhysicsBody{

    public float width,height;
    public Fixture fixtureTop,fixtureBottom,fixtureCenter;
    public Fixture hitBoxLeft,hitBoxRight,hitBoxBottom;


    public PlazmaBody(World world,float x,float y,float width,float height){
        this.width=width;
        this.height=height;

        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/SCALE,y/SCALE);
//        bodyDef.linearDamping=0.5f;

        body=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.density=1f;
        fixtureDef.friction=0.7f;
        fixtureDef.restitution=0.3f;

        PolygonShape shape=new PolygonShape();
        float kw=width/2;
        float kh=height/2-width/2;
        shape.setAsBox(kw/SCALE,kh/SCALE);
        fixtureDef.shape=shape;

        fixtureCenter=body.createFixture(fixtureDef);

        CircleShape circleShape=new CircleShape();
        circleShape.setRadius(width/2f/SCALE);
        fixtureDef.shape=circleShape;

        circleShape.setPosition(new Vector2(0,kh/SCALE));
        fixtureTop=body.createFixture(fixtureDef);

        circleShape.setPosition(new Vector2(0,-kh/SCALE));
//        fixtureDef.restitution=0;
        fixtureBottom=body.createFixture(fixtureDef);

        float hw=5;
        float hh=kh/2;
        fixtureDef.shape=shape;

        float hx=kw+hw;
        shape.setAsBox(
            hw/SCALE,
            hh/SCALE,
            new Vector2(hx/SCALE,0),
            0
        );
        hitBoxRight= body.createFixture(fixtureDef);

        hx=-hx;
        shape.setAsBox(
            hw/SCALE,
            hh/SCALE,
            new Vector2(hx/SCALE,0),
            0
        );
        hitBoxLeft= body.createFixture(fixtureDef);

        shape.setAsBox(
            kw/2/SCALE,
            5/SCALE,
            new Vector2(0,-(height/2+5)/SCALE),
            0
        );
        hitBoxBottom= body.createFixture(fixtureDef);

        hitBoxBottom.setSensor(true);
        hitBoxLeft.setSensor(true);
        hitBoxRight.setSensor(true);

        body.setFixedRotation(true);
    }
    @Override
    public void debugDraw(ShapeRenderer renderer) {
        renderer.setColor(Color.GREEN);
        Vector2 pos=getPosition();
        float kw=width/2;
        float kh=height/2-width/2;
        float r=width/2;

        renderer.rect(pos.x-kw,pos.y-kh,2*kw,2*kh);
        renderer.circle(pos.x,pos.y+kh,r);
        renderer.circle(pos.x,pos.y-kh,r);
    }
}
