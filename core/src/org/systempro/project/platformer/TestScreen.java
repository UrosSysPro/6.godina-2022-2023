package org.systempro.project.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;
import org.systempro.project.physics2d.RectBody;

import java.util.ArrayList;
import java.util.Objects;

public class TestScreen extends BasicScreen {
    TiledMap map;
    TiledMapRenderer mapRenderer;
    Camera2d camera2d;
    ShapeRenderer shapeRenderer;

    World world;
    ArrayList<RectBody> walls;
    RectBody player;

    @Override
    public void show() {
        shapeRenderer=new ShapeRenderer();

        camera2d=new Camera2d();
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera2d.setScale(1,1);
        camera2d.position.set(0,0,0);
        map=new TmxMapLoader().load("platformerMap.tmx");

        world=new World(new Vector2(0,-10),false);
        walls=new ArrayList<>();

        MapObjects o=map.getLayers().get("objects").getObjects();

        for(MapObject object : o){
            if(Objects.equals(object.getName(), "spawn")){
                float x= (float) object.getProperties().get("x");
                float y= (float) object.getProperties().get("y");
                player=new RectBody(world,x+10,y+10,20,20);
                player.body.setFixedRotation(true);
            }else{
                float x= (float) object.getProperties().get("x");
                float y= (float) object.getProperties().get("y");
                float w= (float) object.getProperties().get("width");
                float h= (float) object.getProperties().get("height");
                RectBody wall=new RectBody(world,x+w/2,y+h/2,w,h);
                wall.setType(BodyDef.BodyType.StaticBody);
                walls.add(wall);
            }
        }

        mapRenderer=new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        Vector2 speed=player.getVelocity();
        if(Gdx.input.isKeyJustPressed(Input.Keys.W))speed.y=10;
        if(Gdx.input.isKeyPressed(Input.Keys.A))speed.x=-5;
        if(Gdx.input.isKeyPressed(Input.Keys.D))speed.x=5;
        if(speed.y<-1)speed.y-=10*delta;
        player.setVelocity(speed);

        world.step(delta,10,10);

        float x=player.getPosition().x;
        float y=player.getPosition().y;
        float width=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        camera2d.setTranslation(-x,-y);
        camera2d.update();
        mapRenderer.setView(camera2d.combined4,x-width/2,y-height/2,width,height);

        mapRenderer.render();
        shapeRenderer.setProjectionMatrix(camera2d.combined4);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.debugDraw(shapeRenderer);
        shapeRenderer.end();

        if(Gdx.input.isKeyPressed(Input.Keys.F3)){
            shapeRenderer.setProjectionMatrix(camera2d.combined4);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            player.debugDraw(shapeRenderer);
            for(RectBody wall : walls){
                wall.debugDraw(shapeRenderer);
            }
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        camera2d.setSize(width,height);
    }
}
