package org.systempro.project.physics2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class RectBody extends PhysicsBody {

    private Fixture fixture;
    private float width,height;
    public RectBody(World world,float x,float y,float width,float height){

        this.width=width;
        this.height=height;

        PolygonShape shape=new PolygonShape();
        shape.setAsBox(width/SCALE/2f,height/SCALE/2f);

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
        float a=getAngle()/(float)Math.PI*180f;
        renderer.setColor(Color.GREEN);
        renderer.rect(v.x-width/2f,v.y-height/2f,width/2f,height/2,width,height,1f,1f,a);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
