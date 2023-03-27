package org.systempro.project.normalMapTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.*;
import org.systempro.project.scalaui.widgets.Text;

public class TestScreen extends BasicScreen {
    NormalMappingInstanceRenderer renderer;
    CameraController controller;
    MeshInstance instance;
    float angle;
    @Override
    public void show() {
        SceneAsset asset=new GLBLoader().load(Gdx.files.internal("normalMapping/untitled.glb"));
        Mesh mesh=asset.meshes.first();
        MeshInstance.enableInstancing(mesh,1000);

        for(VertexAttribute attribute:mesh.getVertexAttributes()){
            System.out.println(attribute.numComponents+" "+attribute.alias);
        }

        Texture texture=new Texture("normalMapping/diffuse_metal.jpg");
        Texture normalMap=new Texture("normalMapping/normal_metal.jpg");
        Texture roughnessMap=new Texture("normalMapping/rough_metal.jpg");

        renderer=new NormalMappingInstanceRenderer(mesh,texture).defaultShader().defaultCamera().defaultEnvironment();
        renderer.normalMap=normalMap;
        renderer.roughnessMap=roughnessMap;
        Light light=new Light();
        light.attenuation.set(0,0,1);
        light.position.set(3,3,3);
        renderer.environment.add(light);
        renderer.environment.ambientColor.set(0.1f,0.1f,0.1f,1.0f);

        controller=new CameraController(renderer.camera);
        Gdx.input.setInputProcessor(controller);

        instance=new MeshInstance();
    }

    @Override
    public void render(float delta) {
        controller.update(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
            angle+=0.02;
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2))
            angle-=0.02;
//        angle+=0.03f;
        renderer.environment.lights[0].position.set((float) Math.sin(angle)*3,3,(float) Math.cos(angle)*3);


//        instance.rotation.setEulerAnglesRad(angle,0,0);
//        instance.update();

        renderer.clearScreen(0,0,0,1);
        renderer.enableDepthAndCulling();
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        renderer.draw(instance);
        renderer.flush();
    }

    @Override
    public void resize(int width, int height) {
        renderer.camera.viewportWidth=width;
        renderer.camera.viewportHeight=height;
    }
}
