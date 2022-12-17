package org.systempro.project.scalaui
import com.badlogic.gdx.math.Vector2
import org.systempro.project.ui.{Size, WidgetRenderer}

class MultiChildWidget() extends Widget {
  override def draw(renderer: WidgetRenderer): Unit = ???

  override def calculateSize(maxSize: Size): Size = ???

  override def calculateLocation(parentLocation: Vector2): Vector2 = ???

  override def animate(delta: Float): Unit = ???
}
