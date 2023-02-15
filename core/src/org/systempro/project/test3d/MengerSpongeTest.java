package org.systempro.project.test3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.*;

public class MengerSpongeTest extends BasicScreen {
    ShaderProgram shader;
    Mesh mesh,carMesh;
    Camera camera;
    Texture texture,carTexture;
    CameraController controller;
    Environment environment;

    InstanceRenderer renderer,carRenderer;

    MeshInstance[][][] instances;
    MeshInstance carInstance;

    float time=0;


    public void shaderSetup(){
        ShaderProgram.pedantic=false;
        String vertex=Gdx.files.internal("phongShader/vertex.glsl").readString();
        String fragment=Gdx.files.internal("phongShader/fragment.glsl").readString();
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
        camera.far=100;
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
        texture=new Texture("test3d/texture.png");
        carTexture=new Texture("test3d/auto.png");
        Model model = new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
        mesh = model.meshes.first();
        Model autoModel = new ObjLoader().loadModel(Gdx.files.internal("test3d/auto.obj"));
        carMesh = autoModel.meshes.first();
        shaderSetup();
        cameraSetup();
        environmentSetup();
        cubeSetup(81);
        carRenderer=new InstanceRenderer(carMesh,shader,camera,carTexture,environment);
        renderer=new InstanceRenderer(mesh,shader,camera,texture,environment);

        carInstance=new MeshInstance();
        carInstance.position.set(4.5f,4.5f,4.5f);
        carInstance.update();

        MengerSpongeUI.init(controller);
        Gdx.input.setInputProcessor(new InputMultiplexer(
            MengerSpongeUI.scene().inputProcessor(),
            controller
        ));
    }

    @Override
    public void render(float delta) {

        controller.update(delta);
        time+=delta;
        if(time>Math.PI*2*10)time=0;

        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);

        int instancesRendered=0;
        for(MeshInstance[][] platform:instances){
            for(MeshInstance[] row:platform){
                for(MeshInstance instance:row){
                    renderer.draw(instance);
                    instancesRendered++;
                }
            }
        }
        renderer.flush();

        carRenderer.draw(carInstance);
        instancesRendered++;
        carRenderer.flush();

        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glDisable(GL20.GL_CULL_FACE);


        MengerSpongeUI.fpsKey().widget().text_$eq(""+Gdx.graphics.getFramesPerSecond());
        MengerSpongeUI.instanceNumKey().widget().text_$eq(""+instancesRendered);
        MengerSpongeUI.frameTimeKey().widget().text_$eq(""+delta);
        MengerSpongeUI.scene().animate(delta);
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
