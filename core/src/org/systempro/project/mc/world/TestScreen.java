package org.systempro.project.mc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.CameraController2;
import org.systempro.project.basics3d.MeshInstance;

public class TestScreen extends BasicScreen {

    World world;
    BlockFaceRenderer blockFaceRenderer;
    SkyBoxRenderer skyBoxRenderer;
    CameraController2 controller;
    float time=0;
    @Override
    public void show() {

        skyBoxRenderer=new SkyBoxRenderer();

        world=new World();

        Mesh mesh=new ObjLoader()
            .loadModel(Gdx.files.internal("test3d/kocka.obj"))
            .meshes.first();
        MeshInstance.enableInstancing(mesh,1000);
        Camera camera=new PerspectiveCamera(60,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.near=(float) 0.1;
        camera.far=(float)1000;
        controller=new CameraController2(camera,0.5f,20f,true);
        Gdx.input.setInputProcessor(controller);
        blockFaceRenderer=new BlockFaceRenderer();
        blockFaceRenderer.camera=camera;

        world.generateCache();
    }

    @Override
    public void render(float delta) {
        time+=delta;
        blockFaceRenderer.sunDirection.set((float) Math.cos(time)*3, (float) -1, (float) Math.sin(time)*3).nor();
        controller.update(delta);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT|GL20.GL_COLOR_BUFFER_BIT);

        skyBoxRenderer.draw(controller);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        world.render(blockFaceRenderer);
    }

    @Override
    public void resize(int width, int height) {
        blockFaceRenderer.camera.viewportWidth=width;
        blockFaceRenderer.camera.viewportHeight=height;
    }
}
