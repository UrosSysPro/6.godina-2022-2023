package org.systempro.project.basics3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import org.systempro.project.basics3d.Environment;
import org.systempro.project.basics3d.Light;
import org.systempro.project.basics3d.MeshInstance;

public class ShadowMapRenderer {

    private Mesh mesh;
    private ShaderProgram shader;
    private Camera camera;
    private Vector3 direction;
    public float near=0.1f,far=15f;
    private int maxInstances=1000;
    private int instanceSize=16;
    private int instancesToRender=0;
    private float[] instanceData;

    private FrameBuffer buffer;
    private final int bufferWidth=1024,bufferHeight=1024;

    public ShadowMapRenderer(){
        buffer=new FrameBuffer(Pixmap.Format.RGB888,bufferWidth,bufferHeight,true);

        camera=new OrthographicCamera(10,10);
        camera.position.set(0,0,0);
        camera.near=near;
        camera.far=far;

        camera.update();

        direction=new Vector3();

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

    public Matrix4 getViewMatrix(){
        return camera.view;
    }
    public Matrix4 getProjectionMatrix(){
        return camera.projection;
    }
    public Texture getTexture(){
        return buffer.getColorBufferTexture();
    }

    public Vector3 getDirection() {
        return direction;
    }

    public ShadowMapRenderer setDirection(Vector3 direction) {
        camera.up.set(0,1,0);
        camera.update();
        camera.lookAt(new Vector3(camera.position).add(direction));
        camera.update();
        direction.set(direction).nor();
        return this;
    }
    public ShadowMapRenderer lookAt(Vector3 point) {
        camera.up.set(0,1,0);
        camera.update();
        camera.lookAt(point);
        camera.update();
        direction.set(point.sub(camera.position)).nor();
        return this;
    }
    public ShadowMapRenderer setPostion(Vector3 position){
        camera.up.set(0,1,0);
        camera.update();
        camera.position.set(position);
        camera.lookAt(position.add(direction));
        camera.update();
        return this;
    }

    public ShadowMapRenderer setMesh(Mesh mesh) {
        this.mesh = mesh;
        return this;
    }
}
