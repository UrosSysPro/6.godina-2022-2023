package org.systempro.project.sdf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;
import org.systempro.project.scalaui.Key;
import org.systempro.project.scalaui.Scene;
import org.systempro.project.scalaui.widgets.Text;

public class TestScreen extends BasicScreen {
    Mesh mesh;
    ShaderProgram shader;

    Camera2d camera2d;
    Key<Text> key;
    Scene scene;
    float time=0;
    @Override
    public void show() {
        key=new Key<>(null);
        scene=SceneLoader.load(key);
        mesh=new Mesh(true,4,6,
            new VertexAttribute(Usage.Position,2,"pos")
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
        String vertex=Gdx.files.internal("sdf/vertex.glsl").readString();
        String fragment=Gdx.files.internal("sdf/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);

        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }

        camera2d=new Camera2d();
        camera2d.setScale(1,-1);
        camera2d.setSize(width,height);
        camera2d.setTranslation(-width/2,-height/2);
        camera2d.update();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        time+=delta;

        shader.bind();
        shader.setUniformf("mouse",new Vector2(Gdx.input.getX(),Gdx.input.getY()));
        shader.setUniformf("screenSize",new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        shader.setUniformf("time",time);
        shader.setUniformMatrix("camera",camera2d.combined4);
        mesh.render(shader, GL20.GL_TRIANGLES);

        key.widget().text_$eq("fps"+Gdx.graphics.getFramesPerSecond());
        scene.layout();
        scene.draw();
    }

    @Override
    public void resize(int width, int height) {
        mesh.setVertices(new float[]{
            0,     0,
            0,height,
            width,     0,
            width,height
        });
        camera2d.setSize(width,height);
        camera2d.setTranslation(-width/2,-height/2);
        camera2d.update();
        scene.resize(width,height);
    }
}
