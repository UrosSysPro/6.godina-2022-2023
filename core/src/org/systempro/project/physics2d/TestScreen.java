package org.systempro.project.physics2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

import java.util.ArrayList;
import java.util.Random;

public class TestScreen extends BasicScreen {

    public ShapeRenderer renderer;
    public World world;
    public ArrayList<PhysicsBody> blocks;
    public Random random;
    @Override
    public void show() {
        renderer=new ShapeRenderer();
        world=new World(new Vector2(0,-10f),false);
        blocks=new ArrayList<>();
        random=new Random();

        PhysicsBody body=new RectBody(world,Gdx.graphics.getWidth()/2,20,Gdx.graphics.getWidth(),40);
        body.setType(BodyDef.BodyType.StaticBody);
        blocks.add(body);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        float x=Gdx.input.getX();
        float y=Gdx.graphics.getHeight()-Gdx.input.getY();
        float size=15+random.nextInt(30);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            blocks.add(new RectBody(world,x,y,size,size));
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            blocks.add(new CircleBody(world,x,y,size/2f));
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

