package org.systempro.project.shadow2dtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.renderers.SdfShadow2d;
import org.systempro.project.renderers.TextureRenderer;

public class TestScreen extends BasicScreen {

    TextureRenderer renderer;
    SdfShadow2d shadow;

    @Override
    public void show() {
        float widht=Gdx.graphics.getWidth();
        float height=Gdx.graphics.getHeight();
        shadow=new SdfShadow2d(widht,height,widht,height);
//        renderer=new TextureRenderer();
        renderer=new TextureRenderer(shadow.texture);
        renderer.camera2d.setScale(1,1);
        resize((int) widht, (int) height);
        System.out.println(Gdx.graphics.getGLVersion().getDebugVersionString());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        shadow.render(100);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        renderer.draw(shadow.region,0,0,width,height);
        renderer.flush();
    }

    @Override
    public void resize(int width, int height) {
        renderer.camera2d.setSize(width,height);
        renderer.camera2d.setPosition(width/2,height/2);
        renderer.camera2d.update();
        shadow.resize(width,height,width/10,height/10);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        shadow.dispose();
    }
}
