package org.systempro.project.mc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import org.systempro.project.basics3d.CameraController2;

public class SkyBoxRenderer {
    private Mesh mesh;
    private ShaderProgram shader;

    public SkyBoxRenderer(){
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

        String vertex= Gdx.files.internal("mc/skyBoxRenderer/vertex.glsl").readString();
        String fragment= Gdx.files.internal("mc/skyBoxRenderer/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }
    }

    public void draw(CameraController2 controller){
        shader.bind();
        shader.setUniformMatrix(
            "rotation",
            new Matrix4()
                .rotate(
                    new Quaternion()
                        .setEulerAnglesRad(controller.getYaw(),controller.getPitch(),controller.getRoll())
                )
        );
        mesh.render(shader, GL20.GL_TRIANGLES);
    }
}
