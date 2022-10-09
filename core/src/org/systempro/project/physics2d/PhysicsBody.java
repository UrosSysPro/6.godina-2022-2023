package org.systempro.project.physics2d;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class PhysicsBody {
    public static float SCALE=20;
    public Body body;

    public Vector2 getPosition(){
        Vector2 v = new Vector2(body.getPosition());
        v.x*=SCALE;
        v.y*=SCALE;
        return v;
    }
    public float getAngle(){
        return body.getAngle();
    }
    public abstract void debugDraw(ShapeRenderer renderer);
}
