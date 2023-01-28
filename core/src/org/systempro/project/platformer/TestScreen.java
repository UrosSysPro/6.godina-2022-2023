package org.systempro.project.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;
import org.systempro.project.physics2d.CollisionListener;
import org.systempro.project.physics2d.PlazmaBody;
import org.systempro.project.physics2d.RectBody;

import java.util.ArrayList;
import java.util.Objects;

public class TestScreen extends BasicScreen {
    public TiledMap map;
    public TiledMapRenderer mapRenderer;
    public Camera2d camera2d;
    public ShapeRenderer shapeRenderer;

    public World world;
    public ArrayList<Platform> walls;
    public Player player;

    public void loadMap(String mapFileName){

        map=new TmxMapLoader().load(mapFileName);
        world=new World(new Vector2(0,-10),false);
        walls=new ArrayList<>();

        MapObjects o=map.getLayers().get("objects").getObjects();

        for(MapObject object : o){
            if(Objects.equals(object.getName(), "spawn")){
                float x= (float) object.getProperties().get("x");
                float y= (float) object.getProperties().get("y");
                player=new Player(world,x,y,10,20);
            }else{
                if(object instanceof RectangleMapObject){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    float x= rect.x;
                    float y= rect.y;
                    float w= rect.width;
                    float h= rect.height;
                    walls.add(new Platform(world,x+w/2,y+h/2,w,h));
                }
                if(object instanceof PolygonMapObject){
                    PolygonShape shape=new PolygonShape();
//                    shape.set
                }
            }
        }

        mapRenderer=new OrthogonalTiledMapRenderer(map);

        world.setContactListener(new CollisionListener());
    }

    @Override
    public void show() {
        shapeRenderer=new ShapeRenderer();

        camera2d=new Camera2d();
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera2d.setScale(0.5f,0.5f);
        camera2d.setPosition(0,0);

        loadMap("platformer/platformerMap.tmx");

        Gdx.input.setInputProcessor(new GameInputProcessor(this));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        //update
        player.update(delta);
        world.step(delta,10,10);

        //update camera
        float x=player.hitbox.getPosition().x;
        float y=player.hitbox.getPosition().y;
        float width=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        camera2d.setPosition(x,y);
        camera2d.update();
        shapeRenderer.setProjectionMatrix(camera2d.combined4);
        mapRenderer.setView(camera2d.combined4,x-width/2,y-height/2,width,height);

        int frontLayers=Integer.parseInt((String) map.getProperties().get("frontLayers"));
        int backLayers=Integer.parseInt((String) map.getProperties().get("frontLayers"));

        for(int i=0;i<frontLayers;i++){
            MapLayer layer=map.getLayers().get("front"+i);
            layer.setOffsetX(-x*0.05f*(i+1));
            layer.setOffsetY(y*0.05f*(i+1));
        }
        for(int i=0;i<backLayers;i++){
            MapLayer layer=map.getLayers().get("back"+i);
            layer.setOffsetX(x*0.05f*(i+1));
            layer.setOffsetY(-y*0.05f*(i+1));
        }
        //render
        mapRenderer.render();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(shapeRenderer);
        shapeRenderer.end();

        if(Gdx.input.isKeyPressed(Input.Keys.F3)){
            shapeRenderer.setProjectionMatrix(camera2d.combined4);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            player.draw(shapeRenderer);
            for(Platform wall : walls){
                wall.hitbox.debugDraw(shapeRenderer);
            }
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        shapeRenderer.dispose();
        world.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera2d.setSize(width,height);
    }
}
