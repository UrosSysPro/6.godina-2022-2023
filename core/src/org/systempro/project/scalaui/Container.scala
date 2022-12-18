package org.systempro.project.scalaui

import com.badlogic.gdx.graphics.Color
import org.systempro.project.ui.WidgetRenderer

class Container(child:Widget=null,var color:Color=Color.CLEAR) extends SingleChildWidget(child:Widget) {

  override def draw(renderer: WidgetRenderer): Unit = {
    renderer.draw(position.x, position.y, size.width, size.height,color)
    super.draw(renderer)
  }
}
