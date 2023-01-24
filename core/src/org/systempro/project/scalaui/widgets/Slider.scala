package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.graphics.Color
import org.systempro.project.renderers.WidgetRenderer
import org.systempro.project.scalaui.GestureDetector

class Slider(
              var min:Float=0,
              var max:Float=1,
              var step:Float=0.1f,
              var value:Float=0.5f,
              var backgroundWidth:Float=1,
              var foregroundWidth:Float=1,
              var circleRadius:Float=1,
              var backgroundColor:Color=Color.DARK_GRAY,
              var foregroundColor:Color=Color.GRAY,
              var circleColor:Color=Color.WHITE,
              var direction:Int=0,
              var roundness:Float=0,
              val onChange:Float=>Unit=_->{}
) extends GestureDetector {

  override def draw(renderer: WidgetRenderer): Unit = {
    var padding: Float = 0;
    if(direction==SliderDirection.horizontal){
      padding = (1 - backgroundWidth) * size.height / 2;
    }else{
      padding = (1 - backgroundWidth) * size.width / 2;
    }
    var x: Float = position.x + padding;
    var y: Float = position.y + padding;
    var width: Float = size.width - padding * 2;
    var height: Float = size.height - padding * 2;
    renderer.rect(x, y, width, height, backgroundColor, backgroundColor, (if(direction==SliderDirection.horizontal)height else width) * roundness - 1, 0, 1);

    if (direction == SliderDirection.horizontal) {
      padding = (1 - foregroundWidth) * size.height / 2;
    } else {
      padding = (1 - foregroundWidth) * size.width / 2;
    }
    x = position.x + padding;
    y = position.y + padding;
    width = size.width - padding * 2;
    height = size.height - padding * 2;
    if (direction == SliderDirection.horizontal) {
      width = (value-min) / (max - min) * (width - height) + height;
    } else {
      y+=height;
      height = (value-min) / (max - min) * (height - width) + width;
      y-=height;
    }

    renderer.rect(x, y, width, height, foregroundColor, foregroundColor, (if(direction==SliderDirection.horizontal)height else width) * roundness - 1, 0, 1);

    if (direction == SliderDirection.horizontal) {
      padding = (1 - circleRadius) * size.height / 2;
    } else {
      padding = (1 - circleRadius) * size.width / 2;
    }
    x = position.x + padding;
    y = position.y + padding;
    width = size.width - padding * 2;
    height = size.height - padding * 2;
    if(direction==SliderDirection.horizontal){
      x+=(value-min) / (max - min) * (width-height);
      width=height;
    }else{
      y+=(1-(value-min) / (max - min)) * (height-width);
      height=width;
    }
    renderer.rect(x,y,width,height,circleColor,circleColor,width/2-1,0,1);
  }

  touchDown=(screenX,screenY,_,_)=> {
    if(direction==SliderDirection.horizontal)calculateValue(screenX); else calculateValue(screenY);
    true;
  }

  touchUp =(screenX,screenY,_,_)=> {
    if(direction==SliderDirection.horizontal)calculateValue(screenX); else calculateValue(screenY);
    true;
  }

  touchDragged=(screenX,screenY,_)=> {
    if(direction==SliderDirection.horizontal)calculateValue(screenX); else calculateValue(screenY);
    true;
  }

  scrolled=(amountX,amountY)=>{
//    print(s"$amountX $amountY");
    var value=this.value+(if(amountY>0)step else -step);
    if(value>max)value=max;
    if(value<min)value=min;
    setValue(value);
    onChange(value);
    true
  }

  def calculateValue(mousePos: Float): Unit = {
    var padding:Float=0;
    if(direction==SliderDirection.horizontal){
      padding=(1-foregroundWidth)*size.height/2;
    }else{
      padding=(1-foregroundWidth)*size.width/2;
    }

    val width=size.width-2*padding;
    val height=size.height-2*padding;
    var percent: Float = 0;
    if(direction==SliderDirection.horizontal){
      percent = (mousePos - position.x-padding-height/2) / (width-height);
    }else{
      percent=(mousePos - position.y-padding-width/2) / (height-width);
    }
    if (percent > 1) percent = 1;
    if (percent < 0) percent = 0;

    if(direction==SliderDirection.vertical)percent=1-percent;

    percent *= (max - min);
    percent /= step;
    percent = Math.round(percent) * step;
    percent+=min;
    setValue(percent);
    onChange(value);
  }

  def setValue(value:Float): Unit ={
    this.value=value;
  }
}

object SliderDirection{
  val horizontal:Int=0;
  val vertical:Int=1;
}
