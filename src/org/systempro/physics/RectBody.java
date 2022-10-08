package org.systempro.physics;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class RectBody extends PhysicsBody{
    public Fixture fixture;
    public float width,height;

    public RectBody(World world,float x, float y, float width, float height){
        this.width=width;
        this.height=height;

        PolygonShape shape=new PolygonShape();
        shape.setAsBox(width,height);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.density=1f;
        fixtureDef.restitution=0.4f;
        fixtureDef.friction=0.7f;
        fixtureDef.shape=shape;

        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type= BodyDef.BodyType.DynamicBody;

        body=world.createBody(bodyDef);
        fixture=body.createFixture(fixtureDef);
    }

    @Override
    public void debugDraw(ShapeRenderer renderer) {
        Vector2 pos=getPosition();

        renderer.setColor(Color.WHITE);
        renderer.rect(pos.x,pos.y,width,height);
    }
}
