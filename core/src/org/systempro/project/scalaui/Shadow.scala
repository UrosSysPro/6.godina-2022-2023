package org.systempro.project.scalaui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import org.systempro.project.renderers.WidgetRenderer

class Shadow(
            var offset:Vector2=new Vector2(0,0),
            var spread:Float=0,
            var color:Color=Color.BLACK,
            var blur:Float=0,
            var borderRadius:Float=0
            ) {
  def draw(widgetRenderer: WidgetRenderer,position:Vector2,size:Size):Unit={
    widgetRenderer.rect(
      position.x-spread+offset.x,
      position.y-spread+offset.y,
      size.width+2*spread,
      size.height+2*spread,
      color,
      Color.CLEAR,
      borderRadius,
      0,
      blur
    );
  }
}
