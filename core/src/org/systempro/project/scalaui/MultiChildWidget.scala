package org.systempro.project.scalaui
import com.badlogic.gdx.math.Vector2
import org.systempro.project.ui.{Size, WidgetRenderer}

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
}
