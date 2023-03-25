package org.systempro.project.shadowMapTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.*;
import org.systempro.project.renderers.TextureRenderer;

public class TestScreen extends BasicScreen {

    Mesh mesh;
    MeshInstance[] instances;
    CameraController controller;

    NewInstanceRenderer renderer;

    ShaderProgram shader;
    Camera camera;
    @Override
    public void show() {

        //mesh
        Model model=new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
        mesh=model.meshes.first();
        MeshInstance.enableInstancing(mesh,1000);

        instances=new MeshInstance[26];
        for(int index=0;index<25;index++){
            float i=index%5-2.5f;
            float j=index/5-2.5f;
            MeshInstance instance=new MeshInstance();
            instance.position.set(i,0,j);
            instance.scale.set(1,0.1f,1);
            instances[index]=instance;
            instance.update();
        }
        MeshInstance instance=new MeshInstance();
        instance.position.set(0,3,0);
        instances[25]=instance;
        instance.update();

        String vertex=Gdx.files.internal("newInstanceRenderer/vertex.glsl").readString();
        String fragment=Gdx.files.internal("newInstanceRenderer/fragment.glsl").readString();
        ShaderProgram.pedantic=false;
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }

        camera=new PerspectiveCamera(60,800,600);
        camera.near=0.1f;
        camera.far=100f;
        camera.update();
        controller=new CameraController(camera);
        Gdx.input.setInputProcessor(controller);
        Texture texture=new Texture("test3d/texture.png");
        Environment environment=new Environment();
        Light light=new Light();
        light.position.set(0,3,0);
        light.color.set(Color.WHITE);
        light.attenuation.set(0,0,1.5f);
        environment.ambientColor.set(0.1f,0.1f,0.1f,1f);
        environment.add(light);
        renderer=new NewInstanceRenderer(mesh,shader,camera,texture,environment);
    }

    @Override
    public void render(float delta) {


        controller.update(delta);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);



        for(MeshInstance instance:instances){
            instance.update();
            renderer.draw(instance);
        }
        renderer.flush();

        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            for(MeshInstance instance : instances){
                instance.update();
                renderer.shadowMapRenderer.draw(instance);
            }
            renderer.shadowMapRenderer.flush();
        }
    }
}
