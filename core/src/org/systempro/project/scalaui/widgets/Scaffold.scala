package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.math.Vector2
import org.systempro.project.renderers.WidgetRenderer
import org.systempro.project.scalaui.{GestureDetector, Size, Widget}

import scala.collection.mutable

class Scaffold() extends Widget{
  override def draw(renderer: WidgetRenderer): Unit = ???

  override def calculateSize(maxSize: Size): Size = ???

  override def calculateLocation(parentLocation: Vector2): Vector2 = ???

  override def animate(delta: Float): Unit = ???

  override def addListenersToStack(stack: mutable.Stack[Widget], mousePos: Vector2): Unit = ???

  override def addAllListenersToStack(stack: mutable.Stack[GestureDetector]): Unit = ???
}
