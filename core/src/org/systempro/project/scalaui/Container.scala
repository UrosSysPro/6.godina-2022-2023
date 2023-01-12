package org.systempro.project.scalaui

import com.badlogic.gdx.graphics.Color

class Container(
                 child:Widget=null,
                 var color:Color=Color.CLEAR,
                 var borderColor:Color=Color.CLEAR,
                 var borderRadius:Float=0,
                 var borderWidth:Float=0,
                 var blur:Float=0
               ) extends SingleChildWidget(child:Widget) {

  override def draw(renderer: WidgetRenderer): Unit = {
    renderer.draw(position.x, position.y, size.width, size.height,color,borderColor,borderRadius,borderWidth,blur);
    super.draw(renderer);
  }
}
