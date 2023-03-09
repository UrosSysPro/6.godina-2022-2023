package org.systempro.project.snake3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.InstanceRenderer;
import org.systempro.project.basics3d.MeshInstance;

public class TestScreen extends BasicScreen {

    InstanceRenderer renderer;
    MeshInstance instance;
    @Override
    public void show() {
        renderer=InstanceRenderer.createInstanceRenderer();
        instance=new MeshInstance();
    }

    @Override
    public void render(float delta) {
        renderer.controller.update(delta);
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);

        renderer.draw(instance);
        renderer.flush();
    }
}
