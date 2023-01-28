package org.systempro.project.renderers;

import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

public class ShadowTest extends BasicScreen {
    SdfShadow2d shadow2d;

    @Override
    public void show() {
        shadow2d=new SdfShadow2d();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        shadow2d.render();
    }
}
