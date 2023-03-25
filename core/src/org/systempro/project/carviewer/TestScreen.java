package org.systempro.project.carviewer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.*;


public class TestScreen extends BasicScreen {

    InstanceRenderer renderer;
    MeshInstance instance;
    CameraController controller;
    Texture texture;

    @Override
    public void show() {
        Model model=new ObjLoader().loadModel(Gdx.files.internal("carViewer/auto.obj"));
        Mesh mesh=model.meshes.first();
        MeshInstance.enableInstancing(mesh,1000);

        texture=new Texture("carViewer/auto.png");

        Environment environment=new Environment();
        environment.ambientColor.set(0.1f,0.1f,0.1f,1);

        Light light=new Light();
        light.position.set(0,3,0);
        light.color.set(Color.WHITE);

        environment.add(light);

        ShaderProgram.pedantic=false;

        renderer=new InstanceRenderer(mesh,null,null,texture,environment).defaultCamera().defaultShader();

        instance=new MeshInstance();

        controller=new CameraController(renderer.camera);
        Gdx.input.setInputProcessor(controller);

    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.clearScreen(0,0,0,1);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        renderer.enableDepthAndCulling();

        renderer.draw(instance);
        renderer.flush();
    }
}
