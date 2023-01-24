package org.systempro.project.scalaui

import com.badlogic.gdx.math.Vector2
import org.systempro.project.renderers.WidgetRenderer

import scala.collection.mutable

abstract class Widget {
  var position=new Vector2(0,0);
  var size=new Size(0,0);

  def draw(renderer: WidgetRenderer): Unit

  def calculateSize(maxSize: Size): Size

  def calculateLocation(parentLocation: Vector2): Vector2

  def animate(delta: Float): Unit
  def addListenersToStack(stack:mutable.Stack[Widget],mousePos:Vector2):Unit;
  def addAllListenersToStack(stack:mutable.Stack[GestureDetector]):Unit;
  def containsPoint(point:Vector2):Boolean= {
      point.x>position.x&&
      point.y>position.y&&
      point.x<position.x+size.width&&
      point.y<position.y+size.height
  };
  def setKey[A](key:Key[A]): Widget ={
    this match {
      case a: A=>key.widget=a
      case _=>{}
    }
    this;
  }
}
