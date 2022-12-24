package org.systempro.project.scalaui

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import org.systempro.project.ui.{Size, WidgetRenderer}

import scala.collection.mutable

class SingleChildWidget(var child:Widget=null) extends Widget {

  def setChild(child: Widget): SingleChildWidget = {
    this.child = child
    return this
  }

  def getChild: Widget = child

  override def draw(renderer: WidgetRenderer): Unit = {
    if (child != null) {
      child.draw(renderer)
    }
  }

  override def calculateSize(maxSize: Size): Size = {
    if (child != null) {
      val childSize = child.calculateSize(maxSize)
      size.set(childSize)
    }
    else size.set(maxSize)
    size
  }

  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    position.set(parentLocation)
    if (child != null) child.calculateLocation(position)
    position
  }

  override def animate(delta: Float): Unit = {
    if (child != null) child.animate(delta)
  }

  override def addListenersToStack(stack: mutable.Stack[Widget],mousePos:Vector2): Unit = {
    this match {
      case widget:GestureDetector => stack.push(widget);
    }
    if(child!=null&&child.containsPoint(mousePos)) {
      child.addListenersToStack(stack, mousePos)
    };
  }
}
