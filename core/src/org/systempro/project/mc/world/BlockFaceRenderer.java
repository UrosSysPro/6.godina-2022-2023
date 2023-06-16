package org.systempro.project.mc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public class BlockFaceRenderer {
    private Texture texture;
    private Mesh mesh;
    private ShaderProgram shader;
    public Camera camera;
    private int maxInstances=10000;
    private int instanceSize=6;
    private int instancesToDraw=0;
    private float[] instanceData=new float[maxInstances*instanceSize];
    public Vector3 sunDirection;

    public BlockFaceRenderer(){
        sunDirection=new Vector3(0,-1,0).nor();
        mesh=new Mesh(true,4,6,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"position")
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
            new VertexAttribute(VertexAttributes.Usage.Generic,1,"strana"),
            new VertexAttribute(VertexAttributes.Usage.TextureCoordinates,2,"texCoords"),
            new VertexAttribute(VertexAttributes.Usage.Position,3,"worldPosition")
        );
        ShaderProgram.pedantic=false;
        String vertex="";
        vertex+=Gdx.files.internal("mc/world/math.glsl").readString();
        vertex+=Gdx.files.internal("mc/world/vertex.glsl").readString();
//        String vertex= Gdx.files.internal("mc/vertex.glsl").readString();
        String fragment= Gdx.files.internal("mc/world/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }

        texture=new Texture("mc/world/texture.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
    }

    public void draw(BlockFaceCache cache, int chunkX, int chunkY){
        if(instancesToDraw>=maxInstances)flush();
        int index=instancesToDraw*instanceSize;
        instanceData[index+0]=cache.strana;
        instanceData[index+1]=cache.block.textureX+(cache.strana==4?1:0);//cache.block.texCoordX
        instanceData[index+2]=cache.block.textureY;//cache.block.texCoordY
        instanceData[index+3]=chunkX*16+cache.localPositionX;
        instanceData[index+4]=cache.localPositionY;
        instanceData[index+5]=chunkY*16+cache.localPositionZ;
        instancesToDraw++;
    }
    public void flush(){
        shader.bind();
        shader.setUniformMatrix("view",camera.view);
        shader.setUniformMatrix("projection",camera.projection);
        texture.bind(0);
        shader.setUniformi("texture0",0);
        shader.setUniformf("textureWidth",texture.getWidth());
        shader.setUniformf("textureHeight",texture.getWidth());
        shader.setUniformf("sunDirection",sunDirection);
        shader.setUniformf("cameraPosition",camera.position);
        mesh.setInstanceData(instanceData,0,instancesToDraw*instanceSize);
        mesh.render(shader,GL20.GL_TRIANGLES);
        instancesToDraw=0;
    }
}
