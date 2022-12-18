package org.systempro.project.scalaui
import com.badlogic.gdx.math.Vector2
import org.systempro.project.ui.{Size, WidgetRenderer}

class Row(children:List[Widget]=null)extends MultiChildWidget (children:List[Widget]){

  override def calculateSize(maxSize: Size): Size = {
    size.set(maxSize);
    var totalWidth:Float=0;
    var maxHeight:Float=0;
    if(children!=null){
      for(child<-children){
        val childSize = child.calculateSize(size);
        totalWidth+=childSize.width;
        if(childSize.height>maxHeight)maxHeight=childSize.height;
      }
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
