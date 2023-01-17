package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.math.Vector2
import org.systempro.project.scalaui.{MultiChildWidget, Widget}
import org.systempro.project.ui.Size

class Row(children:List[Widget]=null)extends MultiChildWidget (children:List[Widget]){

  override def calculateSize(maxSize: Size): Size = {
    size.set(maxSize);
    var totalWidth:Float=0;
    var maxHeight:Float=0;
    if(children!=null){

      var totalFlex:Float=0;
      children.foreach {
        case expanded: Expanded => {
          totalFlex += expanded.flex;
        }
        case child: Widget => {
          val childSize = child.calculateSize(size);
          totalWidth += childSize.width;
          maxHeight = Math.max(maxHeight, childSize.height);
        }
      }

      val remainingWidth=size.width-totalWidth;
      children.foreach {
        case expanded: Expanded => {
          size.set(expanded.flex/totalFlex*remainingWidth,maxSize.height);
          val childSize = expanded.calculateSize(size);
          totalWidth += childSize.width;
          maxHeight = Math.max(maxHeight, childSize.height);
        }
        case _ => ;
      };

      size.set(totalWidth,maxHeight);
    }
    size;
  }

  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    position.set(parentLocation);
    var currentX=parentLocation.x;
    if(children!=null){
      for(child<-children){
        position.x=currentX;
        child.calculateLocation(position);
        currentX+=child.size.width;
      }
    }
    position.set(parentLocation);
  }

}
