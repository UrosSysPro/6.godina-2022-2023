package org.systempro.project.coolShaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

public class MandelbrotSet extends BasicScreen {
    public Mesh mesh;
    public ShaderProgram shader;
    public float zoom=1,x=0,y=0;

    @Override
    public void show() {
        mesh=new Mesh(true,4,6,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"pos")
        );
        mesh.setVertices(new float[]{
           -1,-1,
            1,-1,
           -1, 1,
            1, 1
        });
        mesh.setIndices(new short[]{
            0,1,2,
            1,3,2
        });

        String vertex= Gdx.files.internal("mandelbrotset/vertex.glsl").readString();
        String fragment=Gdx.files.internal("mandelbrotset/fragment.glsl").readString();

        ShaderProgram.pedantic=false;
        shader=new ShaderProgram(vertex,fragment);
        shader.bind();
        float w=Gdx.graphics.getWidth();
        float h=Gdx.graphics.getHeight();
        shader.setUniform2fv("screenSize",new float[]{w,h},0,2);
//        shader.setUniform2fv("screenSize",new float[]{1,1},0,2);

        System.out.println("shader:"+shader.getLog());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        if(Gdx.input.isKeyPressed(Input.Keys.W))y+=0.05*zoom;
        if(Gdx.input.isKeyPressed(Input.Keys.S))y-=0.05*zoom;
        if(Gdx.input.isKeyPressed(Input.Keys.A))x-=0.05*zoom;
        if(Gdx.input.isKeyPressed(Input.Keys.D))x+=0.05*zoom;
        if(Gdx.input.isKeyPressed(Input.Keys.Q))zoom*=0.95;
        if(Gdx.input.isKeyPressed(Input.Keys.E))zoom*=1.05;

        shader.bind();
        shader.setUniform3fv("info",new float[]{x,y,zoom},0,3);
        mesh.render(shader, GL20.GL_TRIANGLES);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        shader.bind();
        float w=Gdx.graphics.getWidth();
        float h=Gdx.graphics.getHeight();
        shader.setUniform2fv("screenSize",new float[]{w,h},0,2);

    }
}
