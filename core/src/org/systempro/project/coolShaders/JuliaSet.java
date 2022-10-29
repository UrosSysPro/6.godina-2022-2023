package org.systempro.project.coolShaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

public class JuliaSet extends BasicScreen {
    public Mesh mesh;
    public ShaderProgram shader;

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
        String vertex= Gdx.files.internal("juliaset/vertex.glsl").readString();
        String fragment= Gdx.files.internal("juliaset/fragment.glsl").readString();

        ShaderProgram.pedantic=false;
        shader=new ShaderProgram(vertex,fragment);
        shader.bind();
        float w=Gdx.graphics.getWidth();
        float h=Gdx.graphics.getHeight();
        shader.setUniform2fv("screenSize",new float[]{w,h},0,2);
        System.out.println(shader.getLog());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        Vector2 c=new Vector2();
        c.x=(float) Gdx.input.getX()/Gdx.graphics.getWidth();
        c.y=(float) Gdx.input.getY()/Gdx.graphics.getHeight();
        c.x=c.x*2-1;
        c.y=c.y*2-1;

        shader.bind();
        shader.setUniformf("mouse",c);
        mesh.render(shader, GL20.GL_TRIANGLES);
    }

    @Override
    public void resize(int width, int height) {
        shader.bind();
        float w=Gdx.graphics.getWidth();
        float h=Gdx.graphics.getHeight();
        shader.setUniform2fv("screenSize",new float[]{w,h},0,2);
    }

    @Override
    public void dispose() {

    }
}
