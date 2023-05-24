package org.systempro.project.mc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.CameraController;
import org.systempro.project.basics3d.CameraController2;
import org.systempro.project.basics3d.InstanceRenderer;
import org.systempro.project.basics3d.MeshInstance;

public class TestScreen extends BasicScreen {

    World world;
    InstanceRenderer renderer;
    BlockFaceRenderer blockFaceRenderer;
    SkyBoxRenderer skyBoxRenderer;
    CameraController2 controller;
    @Override
    public void show() {

        skyBoxRenderer=new SkyBoxRenderer();

        world=new World();

        Mesh mesh=new ObjLoader()
            .loadModel(Gdx.files.internal("test3d/kocka.obj"))
            .meshes.first();
        MeshInstance.enableInstancing(mesh,1000);
        Texture texture=new Texture("test3d/texture.png");
        renderer=new InstanceRenderer(mesh,texture)
            .defaultEnvironment()
            .defaultCamera()
            .defaultShader();
        renderer.camera.far=1000;
        renderer.camera.update();
        controller=new CameraController2(renderer.camera,0.5f,2f,true);
        Gdx.input.setInputProcessor(controller);
        blockFaceRenderer=new BlockFaceRenderer();
        blockFaceRenderer.camera=renderer.camera;
        world.generateCache();

        int worldSize= world.calculateSize();
        int cacheSize=world.calculateCacheSize();
        System.out.println(worldSize+"bytes");
        System.out.println(worldSize/1024+"kb");
        System.out.println(worldSize/1024/1024+"mb");
        System.out.println(cacheSize+"bytes");
        System.out.println(cacheSize/1024+"kb");
        System.out.println(cacheSize/1024/1024+"mb");
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        renderer.clearScreen(0,0,0,1);

        skyBoxRenderer.draw(controller.getDirection());

        renderer.enableDepthAndCulling();
        Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        world.render(blockFaceRenderer);
    }

    @Override
    public void resize(int width, int height) {
        blockFaceRenderer.camera.viewportWidth=width;
        blockFaceRenderer.camera.viewportHeight=height;
    }
}
