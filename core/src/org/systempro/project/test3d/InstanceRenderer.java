package org.systempro.project.test3d;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class InstanceRenderer {

    public Mesh mesh;
    public ShaderProgram shader;
    public Camera camera;
    public Texture texture;
    public int maxInstances=1000;
    public int instanceSize=16;
    public int instancesToRender=0;
    public float[] instanceData;

    public InstanceRenderer(Mesh mesh,ShaderProgram shader,Camera camera,Texture texture){
        this.texture=texture;
        this.mesh=mesh;
        this.shader=shader;
        this.camera=camera;
        instanceData=new float[maxInstances*instanceSize];
        mesh.enableInstancedRendering(false,maxInstances,
            new VertexAttribute(Usage.Generic,4,"col0"),
            new VertexAttribute(Usage.Generic,4,"col1"),
            new VertexAttribute(Usage.Generic,4,"col2"),
            new VertexAttribute(Usage.Generic,4,"col3")
        );
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
        texture.bind(0);
        shader.setUniformi("texture0",0);
        shader.setUniformMatrix("view",camera.view);
        shader.setUniformMatrix("projection",camera.projection);
        shader.setUniformf("cameraPosition",camera.position);
        mesh.setInstanceData(instanceData,0,instancesToRender*instanceSize);
        mesh.render(shader, GL20.GL_TRIANGLES);
        instancesToRender=0;
    }
}
