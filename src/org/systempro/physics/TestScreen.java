package org.systempro.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class TestScreen implements Screen {

    public ArrayList<RectBody> blokovi;
    public ShapeRenderer renderer;
    public World world;

    @Override
    public void show() {
        renderer=new ShapeRenderer();
        blokovi=new ArrayList<>();
        world=new World(new Vector2(0,-10f),false);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1,1,1,1);

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            float x=Gdx.input.getX();
            float y=Gdx.input.getY();
            blokovi.add(new RectBody(world,x,y,10,10));
        }

        world.step(delta,10,10);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for(RectBody body:blokovi){
            body.debugDraw(renderer);
        }
        renderer.end();
    }
    @Override
    public void dispose() {
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}
