package org.systempro.project.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;

public class TestScreen extends BasicScreen {
    TiledMap map;
    TiledMapRenderer mapRenderer;
    Camera2d camera2d;

    float x,y,width,height;

    @Override
    public void show() {
        x=0;y=0;width=Gdx.graphics.getWidth();height=Gdx.graphics.getHeight();
        camera2d=new Camera2d();
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera2d.setScale(1,1);
        camera2d.position.set(0,0,0);
        map=new TmxMapLoader().load("platformerMap.tmx");

        mapRenderer=new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        int speed=2;
        if(Gdx.input.isKeyPressed(Input.Keys.W))y-=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.S))y+=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.A))x+=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.D))x-=speed;

        camera2d.setTranslation(x,y);

        camera2d.update();
        mapRenderer.setView(camera2d.combined4,-x-width/2,-y-height/2,width,height);

//        map.getLayers().get("back1").setOffsetX(-x/10);
//        map.getLayers().get("back1").setOffsetY(y/10);
//        map.getLayers().get("front1").setOffsetX(x/10);
//        map.getLayers().get("front1").setOffsetY(-y/10);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        width=Gdx.graphics.getWidth();
        height=Gdx.graphics.getHeight();
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }
}
