package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.math.Vector2
import org.systempro.project.scalaui.{EdgeInsets, SingleChildWidget, Widget}
import org.systempro.project.ui.Size

class Padding(child:Widget=null,padding:EdgeInsets=EdgeInsets.all(0)) extends SingleChildWidget(child:Widget) {

  override def calculateSize(maxSize: Size): Size = {
    val child = getChild
    if (child != null) {
      size.set(maxSize)
      size.width -= padding.left+padding.right;
      size.height -= padding.top+padding.bottom;
      val childSize = child.calculateSize(size)
      size.set(childSize)
      size.width += padding.left + padding.right;
      size.height += padding.top + padding.bottom;
    }
    else size.set(maxSize)
    size
  }

  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    val child = getChild
    if (child != null) {
      position.set(parentLocation)
      position.add(padding.left, padding.top)
      child.calculateLocation(position)
      position.set(parentLocation)
    }
    else position.set(parentLocation)
    position
  }
}
