package org.systempro.project.basics3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.systempro.project.basics3d.Environment;
import org.systempro.project.basics3d.Light;
import org.systempro.project.basics3d.MeshInstance;

public class ShadowMapRenderer {

    public Mesh mesh;
    public ShaderProgram shader;
    public Camera camera;
    public float near=0.1f,far=100f;
    public int maxInstances=1000;
    public int instanceSize=16;
    public int instancesToRender=0;
    public float[] instanceData;

    public FrameBuffer buffer;
    public final int bufferWidth=1024,bufferHeight=1024;

    public ShadowMapRenderer(Mesh mesh){
        buffer=new FrameBuffer(Pixmap.Format.RGB888,bufferWidth,bufferHeight,true);

        this.mesh=mesh;

        camera=new PerspectiveCamera(60,bufferWidth,bufferHeight);
        camera.position.set(5,5,5);
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
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
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
    }
}
