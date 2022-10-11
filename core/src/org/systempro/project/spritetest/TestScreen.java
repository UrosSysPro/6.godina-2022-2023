package org.systempro.project.spritetest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

import java.io.File;

public class TestScreen extends BasicScreen {
    public Texture spriteSheet;
    public TextureRegion[][] regions;
    public Sprite character;
    public SpriteBatch batch;
    public int size=16,scale=5,width,height;
    @Override
    public void show() {
        width=Gdx.graphics.getWidth();
        height=Gdx.graphics.getHeight();

        spriteSheet=new Texture(Gdx.files.internal("spriteSheet.png"));
        regions=TextureRegion.split(spriteSheet,size,size);
        batch=new SpriteBatch();

        character=new Sprite(regions[7][1]);
        character.setPosition(300,300);
        character.setScale(scale);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        //input
        float x=character.getX();
        float y=character.getY();
        float speed=3;

        if(Gdx.input.isKeyPressed(Input.Keys.W))y+=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.S))y-=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.D))x+=speed;
        if(Gdx.input.isKeyPressed(Input.Keys.A))x-=speed;
        //update
        character.setPosition(x,y);
        //draw
        batch.begin();
        int w=width/size/scale+1;
        int h=height/size/scale+1;
        for(int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                batch.draw(regions[3][4],i*size*scale,j*size*scale,size*scale,size*scale);
            }
        }
        character.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        spriteSheet.dispose();
    }
}
