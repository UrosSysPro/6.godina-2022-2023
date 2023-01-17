package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.math.Vector2
import org.systempro.project.scalaui.{MultiChildWidget, Widget}
import org.systempro.project.ui.Size

class Column(children:List[Widget]=null)extends MultiChildWidget(children:List[Widget]){
  override def calculateSize(maxSize: Size): Size = {
    size.set(maxSize);
    var totalHeight: Float = 0;
    var maxWidth: Float = 0;

    if (children != null) {
      var totalFlex:Float=0;
      children.foreach{
        case expanded:Expanded=>{
          totalFlex+=expanded.flex;
        }
        case child:Widget=>{
          val childSize = child.calculateSize(size);
          totalHeight += childSize.height;
          maxWidth=Math.max(maxWidth,childSize.width);
        }
      }

      val remainingHeight=maxSize.height-totalHeight;
      children.foreach{
        case expanded: Expanded=>{
          size.set(maxSize.width,expanded.flex/totalFlex*remainingHeight);
          val childSize = expanded.calculateSize(size);
          totalHeight += childSize.height;
          maxWidth = Math.max(maxWidth, childSize.width);
        }
        case _ =>;
      }
      size.set(maxWidth, totalHeight);
    }
    size;
  }

  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    position.set(parentLocation);
    var currentY = parentLocation.y;
    if (children != null) {
      for (child <- children) {
        position.y = currentY;
        child.calculateLocation(position);
        currentY += child.size.height;
      }
    }
    position.set(parentLocation);
  }
}
