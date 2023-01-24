package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.graphics.Color
import org.systempro.project.renderers.WidgetRenderer
import org.systempro.project.scalaui.{Shadow, SingleChildWidget, Widget}

class Container(
                 child:Widget=null,
                 var color:Color=Color.CLEAR,
                 var borderColor:Color=Color.CLEAR,
                 var borderRadius:Float=0,
                 var borderWidth:Float=0,
                 var blur:Float=0,
                 var shadows:List[Shadow]=List()
               ) extends SingleChildWidget(child:Widget) {

  override def draw(renderer: WidgetRenderer): Unit = {
    for(shadow<-shadows){
      shadow.draw(renderer,position,size);
    }
    renderer.rect(position.x, position.y, size.width, size.height,color,borderColor,borderRadius,borderWidth,blur);
    super.draw(renderer);
  }
}
