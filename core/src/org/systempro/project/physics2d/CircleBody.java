package org.systempro.project.physics2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CircleBody extends PhysicsBody{

    private Fixture fixture;
    private float r;
    public CircleBody(World world, float x, float y, float r){
        this.r=r;

        CircleShape shape=new CircleShape();
        shape.setRadius(r/ SCALE);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.density=1f;
        fixtureDef.friction=0.7f;
        fixtureDef.restitution=0.3f;
        fixtureDef.shape=shape;

        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(x/SCALE,y/SCALE);
        bodyDef.type= BodyDef.BodyType.DynamicBody;

        body=world.createBody(bodyDef);
        fixture=body.createFixture(fixtureDef);
    }

    @Override
    public void debugDraw(ShapeRenderer renderer) {
        Vector2 v=getPosition();
        renderer.setColor(Color.RED);
        renderer.circle(v.x,v.y,r);
    }
}
