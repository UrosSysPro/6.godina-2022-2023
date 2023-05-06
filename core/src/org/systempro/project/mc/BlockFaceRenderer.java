package org.systempro.project.mc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BlockFaceRenderer {
    private Mesh mesh;
    private ShaderProgram shader;
    public Camera camera;
    private int maxInstances=10000;
    private int instanceSize=6;
    private int instancesToDraw=0;
    private float[] instanceData=new float[maxInstances*instanceSize];

    public BlockFaceRenderer(){
        mesh=new Mesh(true,4,6,
            new VertexAttribute(Usage.Position,2,"position")
        );
        mesh.setVertices(new float[]{
            -0.5f,-0.5f,
             0.5f, 0.5f,
            -0.5f, 0.5f,
             0.5f,-0.5f,
        });
        mesh.setIndices(new short[]{
           0,1,2,
           0,3,1
        });
        mesh.enableInstancedRendering(true,maxInstances,
            new VertexAttribute(Usage.Generic,1,"strana"),
            new VertexAttribute(Usage.TextureCoordinates,2,"texCoords"),
            new VertexAttribute(Usage.Position,3,"worldPosition")
        );
        ShaderProgram.pedantic=false;
        String vertex= Gdx.files.internal("mc/vertex.glsl").readString();
        String fragment= Gdx.files.internal("mc/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }
    }

    public void draw(BlockFaceCache cache,int chunkX,int chunkY){
        if(instancesToDraw>=maxInstances)flush();
        int index=instancesToDraw*instanceSize;
        instanceData[index+0]=cache.strana;
        instanceData[index+1]=0;//cache.block.texCoordX
        instanceData[index+2]=0;//cache.block.texCoordY
        instanceData[index+3]=chunkX*16+cache.localPositionX;
        instanceData[index+4]=cache.localPositionY;
        instanceData[index+5]=chunkY*16+cache.localPositionZ;
        instancesToDraw++;
    }
    public void flush(){
        shader.bind();
        shader.setUniformMatrix("view",camera.view);
        shader.setUniformMatrix("projection",camera.projection);
        mesh.setInstanceData(instanceData,0,instancesToDraw*instanceSize);
        mesh.render(shader,GL20.GL_TRIANGLES);
        instancesToDraw=0;
    }
}
