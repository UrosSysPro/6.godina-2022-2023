package org.systempro.project.physics2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

import java.util.ArrayList;

public class TestScreen extends BasicScreen {

    public ShapeRenderer renderer;
    public World world;
    public ArrayList<PhysicsBody> blocks;
    @Override
    public void show() {
        renderer=new ShapeRenderer();
        world=new World(new Vector2(0,-10f),false);
        blocks=new ArrayList<>();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            blocks.add(new RectBody(world,Gdx.input.getX(),Gdx.input.getY(),10,10));
        }

        world.step(delta,10,10);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for(PhysicsBody b:blocks){
            b.debugDraw(renderer);
        }
        renderer.end();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();
    }
}

