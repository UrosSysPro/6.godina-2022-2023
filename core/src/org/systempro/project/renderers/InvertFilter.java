package org.systempro.project.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import org.systempro.project.camera.Camera2d;

public class InvertFilter {

    private final Mesh mesh;
    private final ShaderProgram shader;
    public Camera2d camera2d;

    public InvertFilter(){
        mesh=new Mesh(true,4,6,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"pos")
        );
        float width= Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        mesh.setVertices(new float[]{
            0,     0,
            0,height,
            width,     0,
            width,height
        });
        mesh.setIndices(new short[]{
            0,1,2,
            1,3,2
        });

        ShaderProgram.pedantic=false;
        String vertex=Gdx.files.internal("filters/invert/vertex.glsl").readString();
        String fragment=Gdx.files.internal("filters/invert/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);

        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }

        camera2d=new Camera2d();
        camera2d.setScale(1,-1);
        camera2d.setSize(width,height);
        camera2d.setPosition(width/2,height/2);
        camera2d.update();
    }

    public void render(Texture texture){
        shader.bind();
        shader.setUniformMatrix("camera",camera2d.combined4);
        texture.bind(0);
        shader.setUniformi("u_texture",0);
        mesh.render(shader, GL20.GL_TRIANGLES);
    }
    public void resize(int width,int height){
        camera2d.setSize(width,height);
        camera2d.setPosition(width/2,height/2);
        camera2d.update();
        mesh.setVertices(new float[]{
            0,     0,
            0,height,
            width,     0,
            width,height
        });
    }
}
