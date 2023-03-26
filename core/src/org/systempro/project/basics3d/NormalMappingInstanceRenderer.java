package org.systempro.project.basics3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.systempro.project.basics3d.Environment;
import org.systempro.project.basics3d.Light;
import org.systempro.project.basics3d.MeshInstance;

public class NormalMappingInstanceRenderer {

    public Mesh mesh;
    public ShaderProgram shader;
    public Camera camera;
    public Texture texture,normalMap;

    public Environment environment;
    public int maxInstances=1000;
    public int instanceSize=16;
    public int instancesToRender=0;
    public float[] instanceData;

    public NormalMappingInstanceRenderer(Mesh mesh,ShaderProgram shader,Camera camera,Texture texture,Environment environment){
        this.texture=texture;
        this.mesh=mesh;
        this.shader=shader;
        this.camera=camera;
        this.environment=environment;
        instanceData=new float[maxInstances*instanceSize];
//        mesh.enableInstancedRendering(true,maxInstances,
//            new VertexAttribute(Usage.Generic,4,"col0"),
//            new VertexAttribute(Usage.Generic,4,"col1"),
//            new VertexAttribute(Usage.Generic,4,"col2"),
//            new VertexAttribute(Usage.Generic,4,"col3")
//        );
    }
    public NormalMappingInstanceRenderer(Mesh mesh,Texture texture,Environment environment){
        this(mesh,null,null,texture,environment);
        defaultShader();
        defaultCamera();
    }
    public NormalMappingInstanceRenderer(Mesh mesh,ShaderProgram shader,Camera camera,Texture texture){
        this(mesh,shader,camera,texture,null);
        defaultEnvironment();
    }
    public NormalMappingInstanceRenderer(Mesh mesh,Camera camera,Texture texture){
        this(mesh,null,camera,texture);
        defaultShader();
    }
    public NormalMappingInstanceRenderer(Mesh mesh,Texture texture){
        this(mesh,null,texture);
        defaultCamera();
    }
    public NormalMappingInstanceRenderer defaultCamera(){
        camera=new PerspectiveCamera(60,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.near=0.1f;
        camera.far=100f;
        camera.update();
        return this;
    }
    public NormalMappingInstanceRenderer defaultShader(){
        ShaderProgram.pedantic=false;
        shader=new ShaderProgram(
            Gdx.files.internal("normalMapping/vertex.glsl").readString(),
            Gdx.files.internal("normalMapping/fragment.glsl").readString()
        );
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }
        return this;
    }
    public NormalMappingInstanceRenderer defaultEnvironment(){
        environment=new Environment();
        environment.ambientColor.set(1,1,1,1);
        return this;
    }
    public void draw(MeshInstance instance){
        if(instancesToRender>=maxInstances)flush();
        int offset=instancesToRender*instanceSize;
        float[] data=instance.transform.getValues();
        for(int i=0;i<instanceSize;i++){
            instanceData[offset+i]=data[i];
        }
        instancesToRender++;
    }
    public void flush(){
        shader.bind();
        //materials
        texture.bind(0);
        shader.setUniformi("texture0",0);
        normalMap.bind(1);
        shader.setUniformi("normalMap",1);
        //camera
        shader.setUniformMatrix("view",camera.view);
        shader.setUniformMatrix("projection",camera.projection);
        shader.setUniformf("cameraPosition",camera.position);
        //lights
        shader.setUniformi("lightCount",environment.lightCount);
        for(int i=0;i<environment.lightCount;i++){
            Light l=environment.lights[i];
            shader.setUniformi("lights["+i+"].type",l.type);
            shader.setUniformf("lights["+i+"].position",l.position);
            shader.setUniformf("lights["+i+"].attenuation",l.attenuation);
            shader.setUniformf("lights["+i+"].direction",l.direction);
            shader.setUniformf("lights["+i+"].color",l.color.r,l.color.g,l.color.b);
        }
        shader.setUniformf("ambientColor",
            environment.ambientColor.r,
            environment.ambientColor.g,
            environment.ambientColor.b
        );
        //mseh render
        mesh.setInstanceData(instanceData,0,instancesToRender*instanceSize);
        mesh.render(shader, GL20.GL_TRIANGLES);
        instancesToRender=0;
    }
    public void clearScreen(float r,float g,float b,float a){
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
    }
    public void enableDepthAndCulling(){
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
    }

//    public static InstanceRenderer createInstanceRenderer(){
//        Model model=new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
//        Mesh mesh=model.meshes.first();
//
//        Texture texture=new Texture("test3d/texture.png");
//
//        float width=Gdx.graphics.getWidth();
//        float height=Gdx.graphics.getHeight();
//        Camera camera=new PerspectiveCamera(60,width,height);
//        camera.position.set(0,0,0);
//        camera.near=0.1f;
//        camera.far=100;
//        camera.update();
//
//        controller=new CameraController(camera);
//        Gdx.input.setInputProcessor(controller);
//
//        Environment environment=new Environment();
//        environment.ambientColor.set(1,1,1,1);
////        environment.ambientColor.set(0.1f,0.1f,0.1f,1);
////
////        Light light=new Light();
////        light.position.set(0,3,0);
////        light.color.set(Color.WHITE);
////
////        environment.add(light);
//
//        ShaderProgram.pedantic=false;
//        String vertex=Gdx.files.internal("phongShader/vertex.glsl").readString();
//        String fragment=Gdx.files.internal("phongShader/fragment.glsl").readString();
//        ShaderProgram shader=new ShaderProgram(vertex,fragment);
//        if(!shader.isCompiled()){
//            System.out.println(shader.getLog());
//        }
//
//        return new InstanceRenderer(mesh,shader,camera,texture,environment);
//    }

}
