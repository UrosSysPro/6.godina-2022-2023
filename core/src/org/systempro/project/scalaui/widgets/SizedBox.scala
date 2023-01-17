package org.systempro.project.scalaui.widgets

import org.systempro.project.scalaui.{SingleChildWidget, Widget}
import org.systempro.project.ui.Size

class SizedBox(child:Widget=null,var width:Int=10000,var height:Int=10000)
  extends SingleChildWidget(child:Widget) {

  override def calculateSize(maxSize: Size): Size = {
    val width = Math.min(maxSize.width, this.width);
    val height = Math.min(maxSize.height, this.height);
    size.set(width,height);
    if(child!=null){
      child.calculateSize(size);
    }
    size;
  }
}
