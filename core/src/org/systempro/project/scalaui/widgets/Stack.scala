package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.math.Vector2
import org.systempro.project.scalaui.{MultiChildWidget, Widget}
import org.systempro.project.ui.Size

class Stack(children:List[Widget]=null) extends MultiChildWidget(children:List[Widget]) {
  override def calculateSize(maxSize: Size): Size = {
    size.set(maxSize);
    if(children!=null){
      for(child<-children){
        child.calculateSize(size);
      }
    }
    size;
  }

  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    position.set(parentLocation);
    if (children != null) {
      for (child <- children) {
        child.calculateLocation(position);
      }
    }
    position
  }
}
