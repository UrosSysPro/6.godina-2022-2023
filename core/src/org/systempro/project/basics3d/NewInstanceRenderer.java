package org.systempro.project.basics3d;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.systempro.project.basics3d.Environment;
import org.systempro.project.basics3d.Light;
import org.systempro.project.basics3d.MeshInstance;

public class NewInstanceRenderer {

    public Mesh mesh;
    public ShaderProgram shader;
    public Camera camera;
    public Texture texture;
    public Environment environment;
    public int maxInstances=1000;
    public int instanceSize=16;
    public int instancesToRender=0;
    public float[] instanceData;

    public ShadowMapRenderer shadowMapRenderer;

    public NewInstanceRenderer(Mesh mesh,ShaderProgram shader,Camera camera,Texture texture,Environment environment){
        this.texture=texture;
        this.mesh=mesh;
        this.shader=shader;
        this.camera=camera;
        this.environment=environment;
        instanceData=new float[maxInstances*instanceSize];
        shadowMapRenderer=new ShadowMapRenderer(mesh);
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
        //shadow map
        shader.setUniformMatrix("shadowView",shadowMapRenderer.camera.view);
        shader.setUniformMatrix("shadowProjection",shadowMapRenderer.camera.projection);
        shadowMapRenderer.buffer.getColorBufferTexture().bind(1);
        shader.setUniformi("shadowMap",1);
        //mseh render
        mesh.setInstanceData(instanceData,0,instancesToRender*instanceSize);
        mesh.render(shader, GL20.GL_TRIANGLES);
        instancesToRender=0;
    }

}
