package org.systempro.project.shadow2dtest;

import com.badlogic.gdx.Gdx;
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
        renderer=new TextureRenderer(shadow.texture);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        shadow.render(10);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        renderer.draw(shadow.region,0,0,width,height);
        renderer.flush();
    }

    @Override
    public void resize(int width, int height) {
        renderer.camera2d.setSize(width,height);
        renderer.camera2d.setPosition(width/2,height/2);
        shadow.resize(width,height,width,height);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        shadow.dispose();
    }
}
