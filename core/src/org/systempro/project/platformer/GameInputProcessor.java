package org.systempro.project.platformer;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor {
    float r=0;
    float s=1;
    TestScreen game;
    public GameInputProcessor(TestScreen game){
        this.game=game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode==Input.Keys.W){
            game.player.keyUp=true;
            return true;
        }
        if(keycode==Input.Keys.S){
            game.player.keyDown=true;
            return true;
        }
        if(keycode==Input.Keys.A){
            game.player.keyLeft=true;
            return true;
        }
        if(keycode==Input.Keys.D){
            game.player.keyRight=true;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode==Input.Keys.W){
            game.player.keyUp=false;
            return true;
        }
        if(keycode==Input.Keys.S){
            game.player.keyDown=false;
            return true;
        }
        if(keycode==Input.Keys.A){
            game.player.keyLeft=false;
            return true;
        }
        if(keycode==Input.Keys.D){
            game.player.keyRight=false;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if(character=='e'){
            s*=1.1;
        }
        if(character=='q'){
            s*=0.9;
        }
        game.camera2d.setScale(s,s);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        r+=amountY/5;
        game.camera2d.setRotationRad(r);
        return true;
    }
}
