package org.systempro.project.shadertest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

public class TestScreen extends BasicScreen {

    public Mesh mesh;
    public ShaderProgram shader;

    @Override
    public void show() {
        mesh=new Mesh(true,3,3,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"pos")
        );
        mesh.setVertices(new float[]{
            -0.5f,-0.5f,
             0.5f,-0.5f,
             0   , 0.5f
        });
        mesh.setIndices(new short[]{
            0,1,2
        });
        String vertex= Gdx.files.internal("vertex.glsl").readString();
        String fragment= Gdx.files.internal("fragment.glsl").readString();
//        ShaderProgram.pedantic=false;
        shader=new ShaderProgram(vertex,fragment);
        System.out.println("shader:"+shader.getLog());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        shader.bind();
        mesh.render(shader, GL20.GL_TRIANGLES);
    }

    @Override
    public void dispose() {
        mesh.dispose();
        shader.dispose();
    }
}
