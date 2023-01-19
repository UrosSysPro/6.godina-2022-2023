package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.graphics.Color
import org.systempro.project.scalaui.{AnimationController, GestureDetector, WidgetRenderer}

class Switch(
              var value:Boolean=false,
              var checkedColor:Color=Color.LIME,
              var uncheckedColor:Color=Color.FIREBRICK,
              var padding:Float=2,
              var onChange:Boolean=>Unit= _ ->{}
            ) extends GestureDetector {
  val controller: AnimationController = new AnimationController(
    duration = 300,
    value = (if (value) 1 else 0),
    state = (if (value) AnimationController.end else AnimationController.start),
    onStateChange = (state) => {}
  );
  var touchStart:Long=0;
  val currentColor=new Color();
  override def draw(renderer: WidgetRenderer): Unit = {
    //background
    var color=if(value) checkedColor else uncheckedColor;
    currentColor.set(
      controller.value*checkedColor.r+(1-controller.value)*uncheckedColor.r,
      controller.value*checkedColor.g+(1-controller.value)*uncheckedColor.g,
      controller.value*checkedColor.b+(1-controller.value)*uncheckedColor.b,
      controller.value*checkedColor.a+(1-controller.value)*uncheckedColor.a
    )
    renderer.rect(position.x,position.y,size.width,size.height,currentColor,currentColor,size.height/2-1,0,1);
    //knob
    color=Color.WHITE;
    var x:Float=0;
    val y:Float=position.y+padding;
    val width=size.height-2*padding;
    val height=size.height-2*padding;
//    if(value){
//      x=position.x-padding-width+size.width;
//    }else{
//      x=position.x+padding;
//    }
    x=position.x+padding+controller.value*(size.width-padding*2-width);
    renderer.rect(x,y,width,height,color,color,width/2-1,0,1);
  }

  touchDown=(_,_,_,_)=>{
    touchStart=System.currentTimeMillis();
    true;
  }
  touchDragged=(screenX,screenY,pointer)=>{
    var percent=screenX-position.x-padding-(size.height-2*padding)/2;
    percent/=(size.width-2*padding)-(size.height-2*padding);
    percent=Math.min(1,Math.max(percent,0));
    controller.value=percent;
    true;
  }

  touchUp=(_,_,_,_)=> {
    val time:Long=System.currentTimeMillis();
    if(time - touchStart < 300){
      setValue(!value);
      onChange(value);
    }else{
      setValue(controller.value>0.5f);
      onChange(value);
    }
    true;
  }

  def setValue(value:Boolean):Unit={
    this.value=value;
    if (value)
      controller.state = AnimationController.running;
    else
      controller.state = AnimationController.runningReverse;
  }

  override def animate(delta: Float): Unit = controller.update(delta);
}
