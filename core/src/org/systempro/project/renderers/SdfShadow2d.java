package org.systempro.project.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import org.systempro.project.camera.Camera2d;

public class SdfShadow2d {

    private Mesh mesh;
    private ShaderProgram shader;
    public Camera2d camera2d;

    public SdfShadow2d(){
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
        String vertex=Gdx.files.internal("sdfShadow2d/vertex.glsl").readString();
        String fragment=Gdx.files.internal("sdfShadow2d/fragment.glsl").readString();
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

    public void render(){
        shader.bind();
        shader.setUniformMatrix("camera",camera2d.combined4);
        shader.setUniformf("mouse",new Vector2(Gdx.input.getX(),Gdx.input.getY()));
        mesh.render(shader, GL20.GL_TRIANGLES);
    }
}
