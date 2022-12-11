package org.systempro.project.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

public class TestScreen extends BasicScreen {
    Scene scene;
    Padding p;
    @Override
    public void show() {
        scene=new Scene(
            new Container(
                p=new Padding(
                    new Container()
                ).setPadding(200)
            )
        );
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        p.setPadding(Gdx.input.getX());
        scene.layout();
        scene.draw();
    }

    @Override
    public void resize(int width, int height) {
        scene.resize(width,height);
    }
}
