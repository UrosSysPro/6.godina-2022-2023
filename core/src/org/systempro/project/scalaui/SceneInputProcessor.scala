package org.systempro.project.scalaui

import com.badlogic.gdx.InputProcessor

class SceneInputProcessor extends InputProcessor{
  override def keyDown(keycode: Int): Boolean = {
    false;
  }

  override def keyUp(keycode: Int): Boolean = {
    false;
  }

  override def keyTyped(character: Char): Boolean = {
    false;
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    false;
  }

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    false;
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    false;
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {

    false;
  }

  override def scrolled(amountX: Float, amountY: Float): Boolean = {
    false;
  }
}
