package org.systempro.project.test3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.*;

public class MengerSpngeTest extends BasicScreen {
    ShaderProgram shader;
    Mesh mesh;
    Camera camera;
    Texture texture;
    CameraController controller;
    Environment environment;

    InstanceRenderer renderer;

    MeshInstance[][][] instances;

    float time=0;


    public void shaderSetup(){
        ShaderProgram.pedantic=false;
        String vertex=Gdx.files.internal("test3d/vertex.glsl").readString();
        String fragment=Gdx.files.internal("test3d/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }
    }
    public void cameraSetup(){
        float width=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        camera=new PerspectiveCamera(70,width,height);
        camera.near=0.1f;
        camera.far=50;
        camera.position.set(1,0,1);
        camera.lookAt(0,0,0);
        camera.update();
        controller=new CameraController(camera);
    }
    public void cubeSetup(int n){
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
    public void environmentSetup(){
        environment=new Environment();
//        environment.ambientColor.set(1,1,1,1);
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                for(int k=0;k<2;k++){
                    float x=i==0?-2:11;
                    float y=j==0?-2:11;
                    float z=k==0?-2:11;
                    environment.add(new Light(
                        0,
                        new Vector3(x,y,z),
                        new Vector3(0,0,1),
                        new Vector3(0,0,0),
                        new Color(0.1f,0.1f,0.1f,1.0f)
                    ));
                }
            }
        }
    }
    @Override
    public void show() {

        MengerSpongeUI.init();
        //material
        texture=new Texture("test3d/texture.png");

        //mesh
        Model model = new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
        mesh = model.meshes.first();

        //shader
        shaderSetup();

        //camera
        cameraSetup();

        //environment
        environmentSetup();

        cubeSetup(9);
        renderer=new InstanceRenderer(mesh,shader,camera,texture,environment);
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
        time+=delta;
        if(time>Math.PI*2*10)time=0;

        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);

        shader.setUniformf("time",time);

        for(MeshInstance[][] platform:instances){
            for(MeshInstance[] row:platform){
                for(MeshInstance instance:row){
                    renderer.draw(instance);
                }
            }
        }
        renderer.flush();

//        Gdx.gl20.glViewport(-1,-1,2,2);

        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glDisable(GL20.GL_CULL_FACE);

        MengerSpongeUI.scene().draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth=width;
        camera.viewportHeight=height;
        MengerSpongeUI.scene().resize(width,height);
        MengerSpongeUI.scene().layout();
    }
}
