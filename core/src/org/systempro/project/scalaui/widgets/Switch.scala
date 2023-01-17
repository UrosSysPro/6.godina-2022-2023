package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.graphics.Color
import org.systempro.project.scalaui.{GestureDetector, WidgetRenderer}

class Switch(
              var value:Boolean=false,
              var checkedColor:Color=Color.LIME,
              var uncheckedColor:Color=Color.FIREBRICK,
              var padding:Float=2,
              var onChange:Boolean=>Unit= _ ->{}
            ) extends GestureDetector {
  var touchStart:Long=0;
  override def draw(renderer: WidgetRenderer): Unit = {
    //background
    var color=if(value) checkedColor else uncheckedColor;
    renderer.rect(position.x,position.y,size.width,size.height,color,Color.CLEAR,size.height/2-1,0,1);
    //knob
    color=Color.WHITE;
    var x:Float=0;
    val y:Float=position.y+padding;
    val width=size.height-2*padding;
    val height=size.height-2*padding;
    if(value){
      x=position.x-padding-width+size.width;
    }else{
      x=position.x+padding;
    }
    renderer.rect(x,y,width,height,color,Color.CLEAR,width/2-1,0,1);
  }

  touchDown=(_,_,_,_)=>{
    touchStart=System.currentTimeMillis();
    true;
  }

  touchUp=(_,_,_,_)=> {
    val time:Long=System.currentTimeMillis();
    if(time - touchStart < 300){
      setValue(!value);
      onChange(value);
    }
    true;
  }

  def setValue(value:Boolean):Unit={
    this.value=value;
  }
}
