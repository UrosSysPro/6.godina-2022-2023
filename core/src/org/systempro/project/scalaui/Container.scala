package org.systempro.project.scalaui

import com.badlogic.gdx.graphics.Color

class Container(child:Widget=null,var color:Color=new Color().set(0.5f,0.3f,0.4f,0)) extends SingleChildWidget(child:Widget) {

  override def draw(renderer: WidgetRenderer): Unit = {
    renderer.draw(position.x, position.y, size.width, size.height,color)
    super.draw(renderer)
  }
}
