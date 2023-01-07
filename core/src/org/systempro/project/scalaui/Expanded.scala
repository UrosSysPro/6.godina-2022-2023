package org.systempro.project.scalaui
import org.systempro.project.ui.Size

class Expanded(child:Widget=null,var flex:Float=1) extends SingleChildWidget(child:Widget) {

  override def calculateSize(maxSize: Size): Size = {
    super.calculateSize(maxSize);
    size.set(maxSize);
  }
}
