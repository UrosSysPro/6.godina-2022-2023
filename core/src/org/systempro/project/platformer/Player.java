package org.systempro.project.platformer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.systempro.project.physics2d.PlazmaBody;

public class Player {
    public static final int IN_AIR=0,ON_GROUND=1,WALL_JUMPING=2;
    public PlazmaBody hitbox;
    public boolean keyUp,keyDown,keyLeft,keyRight;
    public int state;

    public Player(PlazmaBody hitbox){
        this.hitbox=hitbox;
        keyDown=false;
        keyRight=false;
        keyUp=false;
        keyLeft=false;
        state=ON_GROUND;
    }
    public void update(float delta){

    }
    public void draw(ShapeRenderer renderer){
        hitbox.debugDraw(renderer);
    }
}
