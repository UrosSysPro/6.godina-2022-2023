package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.math.Vector2
import org.systempro.project.scalaui.{SingleChildWidget, Size, Widget}

class Align(
  child:Widget=null,
  var alignment:Int=Alignment.center,
  var expandVertical:Boolean=true,
  var expandHorizontal:Boolean=true
) extends SingleChildWidget(child:Widget) {

  override def calculateSize(maxSize: Size): Size = {
    size.set(maxSize);
    if(child!=null){
      child.calculateSize(size);
      if(!expandVertical)size.height=child.size.height;
      if(!expandHorizontal)size.width=child.size.width;
    }
    size;
  }

  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    if(child!=null){
      var x         = parentLocation.x;
      var y         = parentLocation.y;
      val vertical = alignment / 3;
      val horizontal = alignment % 3;
      if(vertical==1)y+=size.height/2-child.size.height/2;
      if(horizontal==1)x+=size.width/2-child.size.width/2;
      if(vertical==2)y+=size.height-child.size.height;
      if(horizontal==2)x+=size.width-child.size.width;
      position.set(x,y);
      child.calculateLocation(position);
    }
    position.set(parentLocation);
  }
}
object Alignment{
  val topLeft=0;   val topCenter=1;   val topRight=2;
  val centerLeft=3;val center=4;      val centerRight=5;
  val bottomLeft=6;val bottomCenter=7;val bottomRight=8;
}