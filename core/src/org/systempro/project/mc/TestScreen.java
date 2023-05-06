package org.systempro.project.mc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.CameraController;
import org.systempro.project.basics3d.InstanceRenderer;
import org.systempro.project.basics3d.MeshInstance;

public class TestScreen extends BasicScreen {

    World world;
    InstanceRenderer renderer;
    BlockFaceRenderer blockFaceRenderer;
    CameraController controller;
    @Override
    public void show() {
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
        controller=new CameraController(renderer.camera);
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
        renderer.enableDepthAndCulling();
        renderer.clearScreen(0,0,0,1);

//        MeshInstance instance=new MeshInstance();
//        for(int i=0;i<16*3;i++){
//            for(int j=0;j<64;j++){
//                for(int k=0;k<16*3;k++){
//                    BlockState state=world.getBlockState(i,j,k);
//                    if(state.getBlock()==Block.STONE){
//                        instance.position.set(i,j,k);
//                        instance.update();
//                        renderer.draw(instance);
//                    }
//                }
//            }
//        }
//        renderer.flush();
        world.render(blockFaceRenderer);
    }

    @Override
    public void resize(int width, int height) {
        blockFaceRenderer.camera.viewportWidth=width;
        blockFaceRenderer.camera.viewportHeight=height;
    }
}
