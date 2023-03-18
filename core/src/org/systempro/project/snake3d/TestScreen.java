package org.systempro.project.snake3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.InstanceRenderer;
import org.systempro.project.basics3d.MeshInstance;

public class TestScreen extends BasicScreen {
    public Snake snake;
    public int counter=0;
    public final int maxFrames=6;
    @Override
    public void show() {
        snake=new Snake();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE);

        counter++;

        snake.renderer.controller.update(delta);
        snake.input();
        if(counter%maxFrames==0){
            snake.update();
            counter=0;
        }
        snake.draw(counter,maxFrames);
    }
}
