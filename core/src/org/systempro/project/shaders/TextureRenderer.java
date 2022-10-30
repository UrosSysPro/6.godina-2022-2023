package org.systempro.project.shaders;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class TextureRenderer {
    private int maxRects,rectsToDraw,vertexSize;
    private Mesh mesh;
    private ShaderProgram shader;
    private float[] vertices;

    public TextureRenderer(){

        maxRects=10000;
        rectsToDraw=0;
        vertexSize=4;
        mesh=new Mesh(true,maxRects*4,maxRects*6,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"pos"),
            new VertexAttribute(VertexAttributes.Usage.TextureCoordinates,2,"texCoords")
        );
        short[] indices=new short[maxRects*6];
        vertices=new float[maxRects*4*vertexSize];
        for(int i=0;i<maxRects;i++){
            int index=4*i;
            indices[i*6+0]=(short)(index+0);
            indices[i*6+1]=(short)(index+1);
            indices[i*6+2]=(short)(index+2);
            indices[i*6+3]=(short)(index+1);
            indices[i*6+4]=(short)(index+3);
            indices[i*6+5]=(short)(index+2);
        }
        mesh.setIndices(indices);
    }
    public void draw(TextureRegion region,float x,float y,float width,float height){
        if(rectsToDraw>=maxRects)flush();

        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                int index=rectsToDraw*4*vertexSize+(j*2+i)*vertexSize;
                vertices[index+0]=x+i*width;
                vertices[index+1]=y+j*height;
                vertices[index+2]=region.getRegionX()+i*region.getRegionWidth();
                vertices[index+3]=region.getRegionY()+j*region.getRegionHeight();
            }
        }
        rectsToDraw++;
    }
    public void flush(){
        shader.bind();
        mesh.render(shader, GL20.GL_TRIANGLES,0,rectsToDraw*6);
        rectsToDraw=0;
    }
}
