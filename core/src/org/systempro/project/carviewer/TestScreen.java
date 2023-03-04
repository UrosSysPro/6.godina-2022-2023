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
    Camera camera;
    Texture texture;

    @Override
    public void show() {
        Model model=new ObjLoader().loadModel(Gdx.files.internal("carViewer/auto.obj"));
        Mesh mesh=model.meshes.first();

        texture=new Texture("carViewer/auto.png");

        float width=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        camera=new PerspectiveCamera(60,width,height);
        camera.position.set(0,0,0);
        camera.near=0.1f;
        camera.far=100;
        camera.update();

        controller=new CameraController(camera);
        Gdx.input.setInputProcessor(controller);

        Environment environment=new Environment();
        environment.ambientColor.set(0.1f,0.1f,0.1f,1);

        Light light=new Light();
        light.position.set(0,3,0);
        light.color.set(Color.WHITE);

        environment.add(light);

        ShaderProgram.pedantic=false;
        String vertex=Gdx.files.internal("phongShader/vertex.glsl").readString();
        String fragment=Gdx.files.internal("phongShader/fragment.glsl").readString();
        ShaderProgram shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }

        renderer=new InstanceRenderer(mesh,shader,camera,texture,environment);

        instance=new MeshInstance();
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);

        renderer.draw(instance);
        renderer.flush();
    }
}
