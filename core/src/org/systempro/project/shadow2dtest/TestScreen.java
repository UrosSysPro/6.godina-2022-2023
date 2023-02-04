package org.systempro.project.shadow2dtest;

import org.systempro.project.BasicScreen;
import org.systempro.project.renderers.SdfShadow2d;
import org.systempro.project.renderers.TextureRenderer;

public class TestScreen extends BasicScreen {

    TextureRenderer renderer;
    SdfShadow2d shadow;

    @Override
    public void show() {
        renderer=new TextureRenderer();
        shadow=new SdfShadow2d(1);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
