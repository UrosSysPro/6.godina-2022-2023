package org.systempro.project.test3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import org.systempro.project.BasicScreen;

import java.lang.reflect.Array;

public class TestScreen extends BasicScreen {
    ShaderProgram shader;
    Mesh mesh;
    Camera camera;
    CameraController controller;

    @Override
    public void show() {
        Model model = new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
        mesh = model.meshes.first();

        ShaderProgram.pedantic=false;
        String vertex=Gdx.files.internal("test3d/vertex.glsl").readString();
        String fragment=Gdx.files.internal("test3d/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }
        float width=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        camera=new PerspectiveCamera(70,width,height);
        camera.near=0.1f;
        camera.far=100;
        camera.position.set(1,0,1);
        camera.lookAt(0,0,0);
        camera.update();
        controller=new CameraController(camera);
    }

    @Override
    public void render(float delta) {
        controller.update();

        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        shader.bind();
        shader.setUniformMatrix("view",camera.view);
        shader.setUniformMatrix("projection",camera.projection);
        mesh.render(shader,GL20.GL_TRIANGLES);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth=width;
        camera.viewportHeight=height;
    }
}
