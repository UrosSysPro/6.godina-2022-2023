package org.systempro.project.scalaui
import com.badlogic.gdx.math.Vector2
import org.systempro.project.ui.Size

class Column(children:List[Widget]=null)extends MultiChildWidget(children:List[Widget]){
  override def calculateSize(maxSize: Size): Size = {
    size.set(maxSize);
    var totalHeight: Float = 0;
    var maxWidth: Float = 0;
    if (children != null) {
      for (child <- children) {
        val childSize = child.calculateSize(size);
        totalHeight += childSize.height;
        if (childSize.width > maxWidth) maxWidth = childSize.width;
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
