package org.systempro.project.scalaui
import com.badlogic.gdx.math.Vector2
import org.systempro.project.ui.{Size, WidgetRenderer}

import scala.collection.mutable

abstract class MultiChildWidget(var children:List[Widget]=null) extends Widget {
  override def draw(renderer: WidgetRenderer): Unit = {
    if(children!=null){
      for(child<-children){
        child.draw(renderer);
      }
    }
  }

  override def animate(delta: Float): Unit = {
    if (children != null) {
      for (child <- children) {
        child.animate(delta);
      }
    }
  }

  override def addListenersToStack(stack: mutable.Stack[Widget], mousePos: Vector2): Unit = {
    if(children!=null){
      for(child<-children){
        if(child.containsPoint(mousePos))
          child.addListenersToStack(stack, mousePos);
      }
    }
  }
}
