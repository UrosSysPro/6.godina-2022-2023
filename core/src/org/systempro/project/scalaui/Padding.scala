package org.systempro.project.scalaui
import com.badlogic.gdx.math.Vector2
import org.systempro.project.ui.{Size}

class Padding(child:Widget=null,padding:Int=0) extends SingleChildWidget(child:Widget) {

  override def calculateSize(maxSize: Size): Size = {
    val child = getChild
    if (child != null) {
      size.set(maxSize)
      size.width -= 2 * padding
      size.height -= 2 * padding
      val childSize = child.calculateSize(size)
      size.set(childSize)
      size.width += 2 * padding
      size.height += 2 * padding
    }
    else size.set(maxSize)
    size
  }

  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    val child = getChild
    if (child != null) {
      position.set(parentLocation)
      position.add(padding, padding)
      child.calculateLocation(position)
      position.set(parentLocation)
    }
    else position.set(parentLocation)
    position
  }
}
