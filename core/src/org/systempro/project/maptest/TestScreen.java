package org.systempro.project.maptest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;
import org.systempro.project.physics2d.RectBody;
import org.systempro.project.shaders.TextureRenderer;

import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.Objects;

public class TestScreen extends BasicScreen {
    public TiledMapRenderer mapRenderer;
    public TiledMap mapa;
    public Camera2d camera;
    public int x=0,y=0;
    public float scale=1;

    public Texture texture;
    public TextureRegion[][] regions;
    public TextureRenderer textureRenderer;

    public RectBody player;
    public ArrayList<RectBody> walls;
    public World world;
    ShapeRenderer shapeRenderer;
    @Override
    public void show() {

        shapeRenderer=new ShapeRenderer();
        texture=new Texture(Gdx.files.internal("spriteSheet.png"));
        regions=TextureRegion.split(texture,16,16);
        world=new World(new Vector2(0,-10),false);

        float width=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        camera=new Camera2d();
        camera.setTranslation(0,0);
        camera.setScale(1,1);
        camera.setSize(width,height);
        camera.setRotate(0);
        mapa=new TmxMapLoader().load("mapa.tmx");
        mapRenderer=new OrthoCachedTiledMapRenderer(mapa);

        textureRenderer=new TextureRenderer();
        textureRenderer.camera2d=camera;
        textureRenderer.texture=texture;

        MapLayers layers=mapa.getLayers();
        MapLayer objectsLayer=layers.get("objects");
        MapObjects objects=objectsLayer.getObjects();
        walls=new ArrayList<>();
        for(MapObject o : objects){
            if(Objects.equals(o.getName(), "spawn")){
                float x= (float) o.getProperties().get("x");
                float y= (float) o.getProperties().get("y");
                player=new RectBody(world,x,y,20,20);
            }else{
                float x= (float) o.getProperties().get("x");
                float y= (float) o.getProperties().get("y");
                width= (float) o.getProperties().get("width");
                height= (float) o.getProperties().get("height");
                RectBody wall=new RectBody(world,x+width/2,y+height/2,width,height);
                wall.setType(BodyDef.BodyType.StaticBody);
                walls.add(wall);
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        int speed=2;
        if(Gdx.input.isKeyPressed(Input.Keys.W))y-=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.S))y+=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.A))x-=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.D))x+=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.Q))scale*=1.05;
        if(Gdx.input.isKeyPressed(Input.Keys.E))scale*=0.95;

        world.step(delta,10,10);

        Vector2 pos=player.getPosition();
        camera.setTranslation(-pos.x, -pos.y);
        camera.setScale(scale,scale);
        camera.update();
        mapRenderer.setView(
            camera.combined4,
            -camera.position.x,
            -camera.position.y,
            1f/camera.size.x,
            1f/camera.size.y
        );
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        mapRenderer.render();


        float width=player.getWidth();
        float height=player.getHeight();
        textureRenderer.draw(regions[7][1],pos.x-width/2,pos.y-height/2,width,height);
        textureRenderer.flush();

        if(Gdx.input.isKeyPressed(Input.Keys.F3)) {
            shapeRenderer.setProjectionMatrix(camera.combined4);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            player.debugDraw(shapeRenderer);
            for (RectBody b : walls) {
                b.debugDraw(shapeRenderer);
            }
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
        mapa.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.setSize(width,height);
    }
}
