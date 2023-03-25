package org.systempro.project.basics3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import org.systempro.project.basics3d.Environment;
import org.systempro.project.basics3d.Light;
import org.systempro.project.basics3d.MeshInstance;

public class ShadowMapRenderer {

    public Mesh mesh;
    private ShaderProgram shader;
    public Camera camera;
    public Vector3 direction;
    public float near=0.1f,far=15f;
    private int maxInstances=1000;
    private int instanceSize=16;
    private int instancesToRender=0;
    private float[] instanceData;

    public FrameBuffer buffer;
    public final int bufferWidth=1024,bufferHeight=1024;


    public ShadowMapRenderer(Mesh mesh){
        buffer=new FrameBuffer(Pixmap.Format.RGB888,bufferWidth,bufferHeight,true);

//        this.mesh=mesh;
        this.mesh=new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj")).meshes.first();
        MeshInstance.enableInstancing(this.mesh,1000);

        camera=new OrthographicCamera(10,10);
        camera.position.set(5,10,5);
        camera.lookAt(0,0,0);
        camera.near=near;
        camera.far=far;

        camera.update();

        ShaderProgram.pedantic=false;
        String vertex= Gdx.files.internal("shadowMap/vertex.glsl").readString();
        String fragment=Gdx.files.internal("shadowMap/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }

        instanceData=new float[maxInstances*instanceSize];
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
        buffer.begin();
        Gdx.gl20.glClearColor(1,1,1,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glViewport(0,0,bufferWidth,bufferHeight);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl20.glCullFace(GL20.GL_FRONT);
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);

        shader.bind();
        //camera
        shader.setUniformMatrix("view",camera.view);
        shader.setUniformMatrix("projection",camera.projection);
        shader.setUniformf("near",near);
        shader.setUniformf("far",far);
        //mseh render
        mesh.setInstanceData(instanceData,0,instancesToRender*instanceSize);
        mesh.render(shader, GL20.GL_TRIANGLES);
        instancesToRender=0;
        buffer.end();
        Gdx.gl20.glCullFace(GL20.GL_BACK);
    }

}
