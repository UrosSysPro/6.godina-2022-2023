package org.systempro.project.sdf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class SdfRenderer {
    Mesh mesh;
    ShaderProgram shader;
    public SdfRenderer(String fragmentShader){
        mesh=new Mesh(true,4,6,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"a_position")
        );
        mesh.setVertices(new float[]{
            -1,-1,
            1,-1,
            1, 1,
            -1, 1
        });
        mesh.setIndices(new short[]{
            0,1,2,
            0,2,3
        });
        ShaderProgram.pedantic=false;
        String vertex= Gdx.files.internal("sdfRenderer/vertex.glsl").readString();
        String fragment= fragmentShader;
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }
    }
    public static SdfRenderer fromFile(String fragmentShaderFile){
        return new SdfRenderer(Gdx.files.internal("sdfRenderer/"+fragmentShaderFile).readString());
    }
    public void draw(){
        shader.bind();
        mesh.render(shader, GL20.GL_TRIANGLES);
    }

}
