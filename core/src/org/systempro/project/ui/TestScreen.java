package org.systempro.project.ui;

import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

public class TestScreen extends BasicScreen {
    Scene scene;
    @Override
    public void show() {
        scene=new Scene();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        scene.draw();
    }
}
