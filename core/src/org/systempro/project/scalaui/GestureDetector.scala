package org.systempro.project.scalaui

import com.badlogic.gdx.InputProcessor

class GestureDetector(
  child:Widget=null,var focusable:Boolean=true,
  var keyDown     :(Int)=>            Boolean=(keycode)=>false,
  var keyUp       :(Int)=>            Boolean=(keycode)=>false,
  var keyTyped    :(Char)=>           Boolean=(character)=>false,
  var touchDown   :(Int,Int,Int,Int)=>Boolean=(screenX,screenY,pointer,button)=>false,
  var touchUp     :(Int,Int,Int,Int)=>Boolean=(screenX,screenY,pointer,button)=>false,
  var touchDragged:(Int,Int,Int)=>    Boolean=(screenX,screenY,pointer)=>false,
  var mouseMoved  :(Int,Int)=>        Boolean=(screenX,screenY)=>false,
  var mouseLeave  :(Int,Int)=>        Boolean=(screenX,screenY)=>false,
  var mouseEnter  :(Int,Int)=>        Boolean=(screenX,screenY)=>false,
  var scrolled    :(Float,Float)=>    Boolean=(amountX,amountY)=>false
) extends SingleChildWidget(child:Widget){
  var mouseOver=false;
//  override def keyDown(keycode: Int): Boolean = false;
//
//  override def keyUp(keycode: Int): Boolean = false;
//
//  override def keyTyped(character: Char): Boolean = false;
//
//  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
//
//  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
//
//  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
//
//  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false
//  def mouseLeave(screenX: Int, screenY: Int):Boolean=false;
//  def mouseEnter(screenX: Int, screenY: Int):Boolean=false;
//
//  override def scrolled(amountX: Float, amountY: Float): Boolean = false
}
