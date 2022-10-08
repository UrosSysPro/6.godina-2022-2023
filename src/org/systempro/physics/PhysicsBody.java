package org.systempro.physics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public abstract class PhysicsBody {
    public Body body;

    public Vector2 getPosition(){
        return body.getPosition();
    }
    public float getAngle(){
        return body.getAngle();
    }
    public void setType(BodyDef.BodyType type){
        body.setType(type);
    }
    public abstract void debugDraw(ShapeRenderer renderer);
}
