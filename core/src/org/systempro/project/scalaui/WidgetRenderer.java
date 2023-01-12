package org.systempro.project.scalaui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.Disposable;
import org.systempro.project.camera.Camera2d;

public class WidgetRenderer implements Disposable {

    private int maxRects,rectsToDraw,vertexSize;
    private Mesh mesh;
    private ShaderProgram shader;
    private float[] vertices;
    //    public Texture texture;
    public Camera2d camera2d;
    public WidgetRenderer(){
        maxRects=10000;
        rectsToDraw=0;
        vertexSize=17;

        mesh=new Mesh(true,maxRects*4,maxRects*6,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"pos"),
            new VertexAttribute(VertexAttributes.Usage.TextureCoordinates,2,"texCoords"),
            new VertexAttribute(VertexAttributes.Usage.ColorUnpacked,4,"color"),
            new VertexAttribute(VertexAttributes.Usage.ColorUnpacked,4,"borderColor"),
            new VertexAttribute(VertexAttributes.Usage.Generic,1,"borderRadius"),
            new VertexAttribute(VertexAttributes.Usage.Generic,1,"borderWidth"),
            new VertexAttribute(VertexAttributes.Usage.Generic,1,"blur"),
            new VertexAttribute(VertexAttributes.Usage.Generic,2,"size")
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
        ShaderProgram.pedantic=false;
        String vertex= Gdx.files.internal("scalaui/WidgetRenderer/vertex.glsl").readString();
        String fragment= Gdx.files.internal("scalaui/WidgetRenderer/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled())
            System.out.println("Shader log:"+shader.getLog());
    }
    public void draw(float x, float y, float width, float height,Color color){
        draw(x,y,width,height,color,Color.CLEAR,0,0,0);
    }
    public void draw(float x, float y, float width, float height, Color color, Color borderColor,float borderRadius,float borderWidth,float blur){
        if(rectsToDraw>=maxRects)flush();

        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                int index=rectsToDraw*4*vertexSize+(j*2+i)*vertexSize;
                //pos
                vertices[index+0]=x+i*width;
                vertices[index+1]=y+j*height;
                //texCoords
                vertices[index+2]=i;
                vertices[index+3]=j;
                //color
                vertices[index+4]=color.r;
                vertices[index+5]=color.g;
                vertices[index+6]=color.b;
                vertices[index+7]=color.a;
                //borderColor
                vertices[index+8] =borderColor.r;
                vertices[index+9] =borderColor.g;
                vertices[index+10]=borderColor.b;
                vertices[index+11]=borderColor.a;
                //borderRadius
                vertices[index+12]=borderRadius;
                //borderWidth
                vertices[index+13]=borderWidth;
                //blur
                vertices[index+14]=blur;
                //size
                vertices[index+15]=width;
                vertices[index+16]=height;
            }
        }
        rectsToDraw++;
    }
    public void flush(){
        shader.bind();
        mesh.setVertices(vertices);
        shader.setUniformMatrix("combined",camera2d.combined);
        mesh.render(shader, GL20.GL_TRIANGLES,0,rectsToDraw*6);
        rectsToDraw=0;
    }

    @Override
    public void dispose() {
        shader.dispose();
        mesh.dispose();
    }
}
