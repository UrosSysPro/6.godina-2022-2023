package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.graphics.Color
import org.systempro.project.scalaui.{GestureDetector, Shadow, Widget, WidgetRenderer}

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

  touchDown =(_,_,_,_)=> {
    touchStart=System.currentTimeMillis();
    true;
  }

  touchUp =(_,_,_,_)=> {
    val touchEnd=System.currentTimeMillis();
    if(touchEnd-touchStart<300){
      onClick();
    }
    true;
  }
}
object Button{
  def elevated(onClick:()=>Unit): Button ={
    new Button(onClick=onClick);
  }
  def outlined(onClick:()=>Unit): Button ={
    new Button(onClick=onClick);
  }
}