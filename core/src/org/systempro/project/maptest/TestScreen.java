package org.systempro.project.maptest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;

public class TestScreen extends BasicScreen {
    public TiledMapRenderer mapRenderer;
    public TiledMap mapa;
    public Camera2d camera;
    public int x=0,y=0;
    public float scale=1;
    @Override
    public void show() {
        float width=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        camera=new Camera2d();
        camera.setTranslation(0,0);
        camera.setScale(1,1);
        camera.setSize(width,height);
        camera.setRotate(0);
        mapa=new TmxMapLoader().load("mapa.tmx");
        mapRenderer=new OrthoCachedTiledMapRenderer(mapa);
//        MapLayers layers=mapa.getLayers();

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

        camera.setTranslation(-x,y);
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
