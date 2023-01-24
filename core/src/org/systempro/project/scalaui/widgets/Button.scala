package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.graphics.Color
import org.systempro.project.renderers.WidgetRenderer
import org.systempro.project.scalaui.{GestureDetector, Shadow, Widget}

class Button(
              child:Widget=null,
              var color:Color=Color.CLEAR,
              var borderColor:Color=Color.CLEAR,
              var borderRadius:Float=0,
              var borderWidth:Float=0,
              var blur:Float=0,
              var shadows:List[Shadow]=List(),
              var onClick:()=>Unit=()=>{}
            ) extends GestureDetector(child:Widget) {
  var touchStart:Long=0;

  override def draw(renderer: WidgetRenderer): Unit = {
    for(shadow<-shadows){
      shadow.draw(renderer,position,size);
    }
    renderer.rect(position.x, position.y, size.width, size.height,color,borderColor,borderRadius,borderWidth,blur);
    super.draw(renderer);
  }

  touchDown = (_,_,_,_) => {
    touchStart=System.currentTimeMillis();
    true;
  }

  touchUp = (_,_,_,_) => {
    val touchEnd=System.currentTimeMillis();
    if(touchEnd-touchStart<300){
      onClick();
    }
    true;
  }
}
object Button{
  def elevated(onClick:()=>Unit=()=>{},color:Color=Color.FOREST,borderColor:Color=Color.DARK_GRAY,borderRadius:Float=5,borderWidth:Float=3,child:Widget=null): Button ={
    new Button(
      child=child,
      onClick=onClick,
      color=color,
      borderColor=borderColor,
      borderRadius=borderRadius,
      borderWidth=borderWidth,
      blur = 1
    );
  }
  def outlined(onClick:()=>Unit=()=>{},borderColor:Color=Color.FOREST,borderRadius:Float=10,borderWidth:Float=4,child:Widget=null): Button ={
    new Button(
      child=child,
      onClick=onClick,
      borderColor=borderColor,
      borderWidth=borderWidth,
      borderRadius=borderRadius,
      blur=1
    );
  }
}