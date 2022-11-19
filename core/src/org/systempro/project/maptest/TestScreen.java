package org.systempro.project.maptest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;

public class TestScreen extends BasicScreen {
    TiledMapRenderer mapRenderer;
    TiledMap mapa;
    Camera2d camera;
    int x=0,y=0;
    float scale=1;
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
        camera.update();
        mapRenderer.setView(camera.combined4,-1000,-1000,1000,1000);
//        mapRenderer.setView();
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        mapa.dispose();
    }
}
