package org.systempro.project.shadowMapTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.*;
import org.systempro.project.renderers.TextureRenderer;

public class TestScreen extends BasicScreen {
    MeshInstance[] instances;
    CameraController controller;
    NewInstanceRenderer renderer;
    public Vector3 point=new Vector3(0,0,0);
    @Override
    public void show() {

        //mesh
        Model model=new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
        Mesh mesh=model.meshes.first();
        MeshInstance.enableInstancing(mesh,1000);

        Model shadowModel=new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
        Mesh shadowMesh=shadowModel.meshes.first();
        MeshInstance.enableInstancing(shadowMesh,1000);


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

        Texture texture=new Texture("test3d/texture.png");

        ShaderProgram.pedantic=false;
        renderer=new NewInstanceRenderer(mesh,null,null,texture,null)
            .defaultCamera()
            .defaultEnvironment()
            .defaultShader();
        renderer.shadowMapRenderer.setMesh(shadowMesh);

        Light light=new Light();
        light.position.set(0,3,0);
        light.color.set(Color.WHITE);
        light.attenuation.set(0,0,1.5f);
        renderer.environment.add(light);
        renderer.environment.ambientColor.set(.1f,.1f,.1f,1);

        controller=new CameraController(renderer.camera);
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);

        renderer.clearScreen(0,0,0,1);
        renderer.enableDepthAndCulling();
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        for(MeshInstance instance:instances){
            instance.update();
            renderer.draw(instance);
        }
        renderer.flush();

//        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))point.x+=1;
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))point.x-=1;
        renderer.shadowMapRenderer.lookAt(point);
            for(MeshInstance instance : instances){
                instance.update();
                renderer.shadowMapRenderer.draw(instance);
            }
            renderer.shadowMapRenderer.flush();
//        }
    }
}
