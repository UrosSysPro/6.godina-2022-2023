package org.systempro.project.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

public class TestScreen extends BasicScreen {
    Texture texture;
    TextureRegion[][] regions;
    Camera2d camera2d;
    SpriteBatch batch;
    @Override
    public void show() {
        batch=new SpriteBatch();
        texture=new Texture(Gdx.files.internal("spriteSheet.png"));
        camera2d=new Camera2d();
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        regions=TextureRegion.split(texture,16,16);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        camera2d.update();

        batch.begin();
        batch.draw(regions[7][0],100,100,100,100);
        batch.end();
    }
}
