package org.systempro.project.scalaui

import com.badlogic.gdx.{Gdx, InputProcessor}
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable

class SceneInputProcessor(var scene:Scene) extends InputProcessor{
  override def keyDown(keycode: Int): Boolean = {
    if (scene.focused != null)
      return scene.focused.keyDown(keycode);
    false;
  }

  override def keyUp(keycode: Int): Boolean = {
    if (scene.focused != null)
      return scene.focused.keyUp(keycode);
    false;
  }

  override def keyTyped(character: Char): Boolean = {
    if (scene.focused != null)
      return scene.focused.keyTyped(character);
    false;
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {

    val allListeners = new mutable.Stack[GestureDetector]();
    scene.root.addAllListenersToStack(allListeners);
//    println(allListeners.size);
    while (allListeners.nonEmpty) {
      val listener = allListeners.pop();
      val mouseover = listener.containsPoint(new Vector2(screenX, screenY));
      if (!mouseover && listener.mouseOver) {
        listener.mouseLeave(screenX, screenY);
      }
      if (mouseover && !listener.mouseOver) {
        listener.mouseEnter(screenX, screenY);
      }
      listener.mouseOver = mouseover;
    }

    val stack = new mutable.Stack[Widget]();
    scene.root.addListenersToStack(stack,new Vector2(screenX,screenY));
    while(stack.nonEmpty){
      val widget=stack.pop().asInstanceOf[GestureDetector];
      val accepted=widget.touchDown(screenX,screenY,pointer,button);
      if(accepted){
        if(widget.focusable){
          scene.focused=widget;
        }
        return true;
      }
    }
    false;
  }

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    if(scene.focused!=null)
      return scene.focused.touchUp(screenX, screenY, pointer, button);
    false;
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    if (scene.focused != null)
      return scene.focused.touchDragged(screenX, screenY, pointer);
    false;
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {

    val allListeners=new mutable.Stack[GestureDetector]();
    scene.root.addAllListenersToStack(allListeners);
//    println(allListeners.size);
    while(allListeners.nonEmpty){
      val listener=allListeners.pop();
      val mouseover=listener.containsPoint(new Vector2(screenX,screenY));
      if(!mouseover&&listener.mouseOver){
        listener.mouseLeave(screenX, screenY);
      }
      if(mouseover&& !listener.mouseOver){
        listener.mouseEnter(screenX,screenY);
      }
      listener.mouseOver=mouseover;
    }

    val stack = new mutable.Stack[Widget]();
    scene.root.addListenersToStack(stack, new Vector2(screenX, screenY));
    while (stack.nonEmpty) {
      val widget = stack.pop().asInstanceOf[GestureDetector];
      val accepted = widget.mouseMoved(screenX, screenY);
      if (accepted) {
        return true;
      }
    }
    false;
  }

  override def scrolled(amountX: Float, amountY: Float): Boolean = {
    val stack = new mutable.Stack[Widget]();
    val screenX=Gdx.input.getX();
    val screenY=Gdx.input.getY();
    scene.root.addListenersToStack(stack, new Vector2(screenX, screenY));
    while (stack.nonEmpty) {
      val widget = stack.pop().asInstanceOf[GestureDetector];
      val accepted = widget.scrolled(amountX, amountY);
      if (accepted) {
        return true;
      }
    }
    false;
  }
}
