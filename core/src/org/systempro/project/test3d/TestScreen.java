package org.systempro.project.test3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    Texture texture;
    CameraController controller;

    InstanceRenderer renderer;

    MeshInstance[][][] instances;

    public int n=27;

    @Override
    public void show() {
        texture=new Texture("test3d/texture.png");

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

        renderer=new InstanceRenderer(mesh,shader,camera,texture);
        instances=new MeshInstance[n][n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                for(int k=0;k<n;k++){
                    float scale=1;
                    int stepen=n;
                    while(stepen>0){
                        int tacnih=0;
                        if((i / stepen)%3 == 1)tacnih++;
                        if((j / stepen)%3 == 1)tacnih++;
                        if((k / stepen)%3 == 1)tacnih++;
                        if(tacnih>=2){
                            scale = 0;
                            break;
                        }
                        stepen/=3;
                    }
                    MeshInstance instance=new MeshInstance();
                    instance.position.set(i,j,k);
                    instance.scale.set(scale,scale,scale);
                    instance.update();
                    instances[i][j][k]=instance;
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            PerspectiveCamera camera1 = (PerspectiveCamera) camera;
            camera1.fieldOfView+=0.5;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            PerspectiveCamera camera1 = (PerspectiveCamera) camera;
            camera1.fieldOfView-=0.5;
        }
        controller.update();

        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                for(int k=0;k<n;k++){
                    renderer.draw(instances[i][j][k]);
                }
            }
        }
        renderer.flush();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth=width;
        camera.viewportHeight=height;
    }
}
