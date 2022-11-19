package org.systempro.project.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    SpriteBatch batch;
    BitmapFont font;
    int x,y;
    float scale,rotate;
    @Override
    public void show() {
        batch=new SpriteBatch();
        font=new BitmapFont();

        texture=new Texture(Gdx.files.internal("spriteSheet.png"));
        camera2d=new Camera2d();
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//        camera2d.setScale(1,-1);
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
        x=0;
        y=0;
        scale=1;
        rotate=0;
    }

    @Override
    public void render(float delta) {
        long start=System.currentTimeMillis();
        ScreenUtils.clear(0,0,0,1);
        int speed=2;
        if(Gdx.input.isKeyPressed(Input.Keys.W))y-=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.S))y+=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.A))x-=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.D))x+=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.Q))scale*=1.05;
        if(Gdx.input.isKeyPressed(Input.Keys.E))scale*=0.95;

        camera2d.setTranslation(-x,-y);
        camera2d.setScale(scale,-scale);
        camera2d.setRotate(rotate);
        camera2d.update();
        int size=16;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i*size*scale-x*scale>Gdx.graphics.getWidth())continue;
                if(j*size*scale-y*scale>Gdx.graphics.getHeight())continue;
                renderer.draw(map[i][j],i*size,j*size,size,size);
            }
        }
        renderer.flush();
        long end=System.currentTimeMillis();
        long frameTime=end-start;
        batch.begin();
        font.draw(batch,""+frameTime,0,15);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera2d.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera2d.setTranslation(-Gdx.graphics.getWidth()/2,-Gdx.graphics.getHeight()/2);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        texture.dispose();
    }
}
