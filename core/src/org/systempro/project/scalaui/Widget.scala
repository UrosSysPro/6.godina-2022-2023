package org.systempro.project.scalaui

import com.badlogic.gdx.math.Vector2
import org.systempro.project.ui.{Size, WidgetRenderer}

abstract class Widget {
  var position=new Vector2(0,0);
  var size=new Size(0,0);

  def draw(renderer: WidgetRenderer): Unit

  def calculateSize(maxSize: Size): Size

  def calculateLocation(parentLocation: Vector2): Vector2

  def animate(delta: Float): Unit
}
