package org.systempro.project.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.scalaui.Fonts;

public class ShadowTest extends BasicScreen {
    SdfShadow2d shadow2d;

    @Override
    public void show() {
        Fonts.load();
        ShadowTestUI.init();
        ShadowTestUI.scene().layout();
        shadow2d=new SdfShadow2d();
        Gdx.input.setInputProcessor(ShadowTestUI.scene().inputProcessor());
    }

    @Override
    public void render(float delta) {
        long start=System.currentTimeMillis();
        ScreenUtils.clear(0,0,0,1);
        shadow2d.render((int) ShadowTestUI.value());
        ShadowTestUI.scene().draw();
        ShadowTestUI.key().widget().text_$eq(""+(System.currentTimeMillis()-start));
    }

    @Override
    public void resize(int width, int height) {
        ShadowTestUI.scene().resize(width,height);
        ShadowTestUI.scene().layout();
        shadow2d.resize(width,height);
    }
}
