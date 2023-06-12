package org.systempro.project.mc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.CameraController2;
import org.systempro.project.basics3d.InstanceRenderer;
import org.systempro.project.basics3d.MeshInstance;
import org.systempro.project.mc.SkyBoxRenderer;

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
        controller=new CameraController2(renderer.camera,0.5f,20f,true);
        Gdx.input.setInputProcessor(controller);
        blockFaceRenderer=new BlockFaceRenderer();
        blockFaceRenderer.camera=renderer.camera;

        world.generateCache();

        System.out.println(new BlockPos().hashCode()==new BlockPos().hashCode());
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
