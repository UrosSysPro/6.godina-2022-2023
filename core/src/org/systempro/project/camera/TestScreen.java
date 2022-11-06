package org.systempro.project.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.shaders.TextureRenderer;

import java.util.Random;

public class TestScreen extends BasicScreen {
    Texture texture;
    TextureRegion[][] regions;
    TextureRegion[][] map;
    Camera2d camera2d;
    TextureRenderer renderer;
    public Random random;
    int n;
    @Override
    public void show() {
        texture=new Texture(Gdx.files.internal("spriteSheet.png"));
        camera2d=new Camera2d();
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera2d.setScale(1,-1);
        camera2d.setTranslation(-Gdx.graphics.getWidth()/2,-Gdx.graphics.getHeight()/2);
        regions=TextureRegion.split(texture,16,16);
        renderer=new TextureRenderer();
        renderer.texture=texture;
        renderer.camera2d=camera2d;

        random=new Random();
        n=100;
        map=new TextureRegion[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){

                map[i][j]=regions[random.nextInt(10)][random.nextInt(10)];
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        camera2d.update();
        int size=16;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                renderer.draw(map[i][j],i*size,j*size,size,size);
            }
        }
        renderer.flush();
    }

    @Override
    public void resize(int width, int height) {
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera2d.setTranslation(-Gdx.graphics.getWidth()/2,-Gdx.graphics.getHeight()/2);
    }
}
