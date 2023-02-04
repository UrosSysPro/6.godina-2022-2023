package org.systempro.project.test3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import org.systempro.project.BasicScreen;

import java.lang.reflect.Array;

public class TestScreen extends BasicScreen {
    ShaderProgram shader;
    Mesh mesh;

    @Override
    public void show() {
        Model model = new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
        mesh = model.meshes.first();

        for(VertexAttribute vertexAttribute :mesh.getVertexAttributes()){
            System.out.println(vertexAttribute.alias);
            System.out.println(vertexAttribute.numComponents);
        }
    }

    @Override
    public void render(float delta) {

    }
}
