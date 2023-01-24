package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.math.Vector2
import org.systempro.project.renderers.WidgetRenderer
import org.systempro.project.scalaui.{GestureDetector, SingleChildWidget, Size, Widget}

import scala.collection.mutable

class Visible(child:Widget=null,var visible:Boolean=true) extends SingleChildWidget(child:Widget) {
  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    if (visible) {
      super.calculateLocation(parentLocation)
    } else {
      position.set(0, 0);
    }
  }

  override def addAllListenersToStack(stack: mutable.Stack[GestureDetector]): Unit = {
    if(visible){
      super.addAllListenersToStack(stack);
    }
  }

  override def calculateSize(maxSize: Size): Size = {
    if(visible)
      super.calculateSize(maxSize);
    else
      size.set(0,0);
  }

  override def draw(renderer: WidgetRenderer): Unit = {
    if(visible)super.draw(renderer);
  }
}
