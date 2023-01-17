package org.systempro.project.scalaui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import org.systempro.project.camera.Camera2d;

public class WidgetRenderer implements Disposable {

    private int maxRects,rectsToDraw,vertexSize;
    private Mesh mesh;
    private ShaderProgram shader;
    private float[] vertices;
    public Texture font=null;
    //    public Texture texture;
    public Camera2d camera2d;
    public WidgetRenderer(){
        maxRects=10000;
        rectsToDraw=0;
        vertexSize=18;

        mesh=new Mesh(true,maxRects*4,maxRects*6,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"pos"),
            new VertexAttribute(VertexAttributes.Usage.TextureCoordinates,2,"texCoords"),
            new VertexAttribute(VertexAttributes.Usage.ColorUnpacked,4,"color"),
            new VertexAttribute(VertexAttributes.Usage.ColorUnpacked,4,"borderColor"),
            new VertexAttribute(VertexAttributes.Usage.Generic,1,"borderRadius"),
            new VertexAttribute(VertexAttributes.Usage.Generic,1,"borderWidth"),
            new VertexAttribute(VertexAttributes.Usage.Generic,1,"blur"),
            new VertexAttribute(VertexAttributes.Usage.Generic,1,"imageAlpha"),
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
    public void rect(float x, float y, float width, float height, Color color){
        rect(x,y,width,height,color,Color.CLEAR,0,0,0);
    }
    public void rect(float x, float y, float width, float height, Color color, Color borderColor, float borderRadius, float borderWidth, float blur){
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
                //imageAlpha
                vertices[index+15]=0;
                //size
                vertices[index+16]=width;
                vertices[index+17]=height;
            }
        }
        rectsToDraw++;
    }
    public void glyph(BitmapFont.Glyph g,Color color,float x,float y,float width,float height){
        if(rectsToDraw>=maxRects)flush();
        float fontWidth=font.getWidth();
        float fontHeight=font.getHeight();
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                int index=rectsToDraw*4*vertexSize+(j*2+i)*vertexSize;
                //pos
                vertices[index+0]=x+i*width;
                vertices[index+1]=y+j*height;
                //texCoords
                vertices[index+2]=(float) g.srcX/ fontWidth +(float)i*g.width/fontWidth;
                vertices[index+3]=(float) g.srcY/ fontHeight+(float)j*g.height/fontHeight;
//                vertices[index+2]=i;
//                vertices[index+3]=j;

                //color
                vertices[index+4]=color.r;
                vertices[index+5]=color.g;
                vertices[index+6]=color.b;
                vertices[index+7]=color.a;
                //borderColor
                vertices[index+8] =0;
                vertices[index+9] =0;
                vertices[index+10]=0;
                vertices[index+11]=0;
                //borderRadius
                vertices[index+12]=0;
                //borderWidth
                vertices[index+13]=0;
                //blur
                vertices[index+14]=0;
                //imageAlpha
                vertices[index+15]=1;
                //size
                vertices[index+16]=width;
                vertices[index+17]=height;
            }
        }
        rectsToDraw++;
    }
    public void border(Color color,float x,float y,float width,float height,float borderWidth,float borderRadius,float blur){
        rect(x,y,width,height,Color.CLEAR,color,borderRadius,borderWidth,blur);
    }
    public void rrect(Color color,float x,float y,float width,float height,float borderRadius,float blur){
        rect(x,y,width,height,color,Color.CLEAR,borderRadius,0,blur);
    }
    public void verticalGradient(Color bottom,Color top,float x,float y,float width,float height,float borderRadius,float blur){
        if(rectsToDraw>=maxRects)flush();
        int index;
        //00 gore levo
        index=rectsToDraw*4*vertexSize+(0*2+0)*vertexSize;
        vertices[index+4]=top.r;
        vertices[index+5]=top.g;
        vertices[index+6]=top.b;
        vertices[index+7]=top.a;
        //10 gore desno
        index=rectsToDraw*4*vertexSize+(0*2+1)*vertexSize;
        vertices[index+4]=top.r;
        vertices[index+5]=top.g;
        vertices[index+6]=top.b;
        vertices[index+7]=top.a;
        //01 dole levo
        index=rectsToDraw*4*vertexSize+(1*2+0)*vertexSize;
        vertices[index+4]=bottom.r;
        vertices[index+5]=bottom.g;
        vertices[index+6]=bottom.b;
        vertices[index+7]=bottom.a;
        //11 dole desno
        index=rectsToDraw*4*vertexSize+(1*2+1)*vertexSize;
        vertices[index+4]=bottom.r;
        vertices[index+5]=bottom.g;
        vertices[index+6]=bottom.b;
        vertices[index+7]=bottom.a;

        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                index=rectsToDraw*4*vertexSize+(j*2+i)*vertexSize;
                //pos
                vertices[index+0]=x+i*width;
                vertices[index+1]=y+j*height;
                //texCoords
                vertices[index+2]=i;
                vertices[index+3]=j;
                //color
//                vertices[index+4]=color.r;
//                vertices[index+5]=color.g;
//                vertices[index+6]=color.b;
//                vertices[index+7]=color.a;
                //borderColor
                vertices[index+8] =0;
                vertices[index+9] =0;
                vertices[index+10]=0;
                vertices[index+11]=0;
                //borderRadius
                vertices[index+12]=borderRadius;
                //borderWidth
                vertices[index+13]=0;
                //blur
                vertices[index+14]=blur;
                //imageAlpha
                vertices[index+15]=0;
                //size
                vertices[index+16]=width;
                vertices[index+17]=height;
            }
        }
        rectsToDraw++;
    }
    public void horizontalGradient(){

    }
    public void flush(){
        shader.bind();
        mesh.setVertices(vertices);
        shader.setUniformMatrix("combined",camera2d.combined);
        if(font!=null){
            font.bind(0);
            shader.setUniformi("font",0);
        }
        mesh.render(shader, GL20.GL_TRIANGLES,0,rectsToDraw*6);
        rectsToDraw=0;
    }

    @Override
    public void dispose() {
        shader.dispose();
        mesh.dispose();
    }
}
