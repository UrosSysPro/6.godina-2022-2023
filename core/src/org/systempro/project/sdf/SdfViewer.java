package org.systempro.project.sdf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;

public class SdfViewer extends BasicScreen {
    SdfRenderer renderer;
    float x,y,z,yaw,pitch,roll,focusLength;
    Matrix4 combined;
    float time=0;
    @Override
    public void show() {
        combined=new Matrix4();
        float width= Gdx.graphics.getWidth();
        float height= Gdx.graphics.getHeight();
        x=0;
        y=0;
        z=0;
        yaw=0;
        pitch=0;//-(float)Math.PI/2;
        roll=0;
        focusLength=500;
        renderer=SdfRenderer.fromFile("shapes3d.glsl");
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Gdx.input.setCursorCatched(true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.input.setCursorCatched(false);
        }
        if (Gdx.input.isCursorCatched()) {
            int invert = -1;
            yaw += Gdx.input.getDeltaX() / 100f * invert;
            pitch += Gdx.input.getDeltaY() / 100f * invert;
            float speed = 10;

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                x -= (float) Math.sin(yaw) * speed;
                z -= (float) Math.cos(yaw) * speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                x += (float) Math.sin(yaw) * speed;
                z += (float) Math.cos(yaw) * speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                z -= Math.cos(yaw + Math.PI / 2) * speed;
                x -= Math.sin(yaw + Math.PI / 2) * speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                z += Math.cos(yaw + Math.PI / 2) * speed;
                x += Math.sin(yaw + Math.PI / 2) * speed;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                yaw -= 1 / 30f * invert;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                yaw += 1 / 30f * invert;
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                pitch -= 1 / 30f * invert;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                pitch += 1 / 30f * invert;

            if(pitch>Math.PI/2)pitch=(float)Math.PI/2;
            if(pitch<-Math.PI/2)pitch=-(float)Math.PI/2;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) y += speed;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) y -= speed;
        }
        ScreenUtils.clear(Color.BLACK);

        time += delta;
        renderer.shader.bind();
        combined.idt()
            .translate(x,y,z)
            .rotate(new Quaternion().setEulerAnglesRad(yaw, pitch, roll))
        ;
        renderer.shader.setUniformMatrix("camera", combined);
        renderer.shader.setUniformf("time", time);
//        renderer.shader.setUniformf("mouse",new Vector2(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY()));
        renderer.shader.setUniformf("screenSize", new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        renderer.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }
}
