package org.systempro.project.scalaui

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable

class SceneInputProcessor(var scene:Scene) extends InputProcessor{
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
    var stack=new mutable.Stack[Widget]();
    scene.root.addListenersToStack(stack,new Vector2(screenX,screenY));
    println(stack.size);
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
