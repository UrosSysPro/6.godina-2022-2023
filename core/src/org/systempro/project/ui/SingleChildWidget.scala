//package org.systempro.project.ui
//
//import com.badlogic.gdx.math.Vector2
//
//import scala.beans.BeanProperty
//
//class SingleChildWidget extends Widget {
//
//  var child :Widget=null
//    override var position: Vector2 = null
//  override var size: Size = null
//
//  def this(position:Vector2,size:Size)={
//    this();
//    this.position=position;
//    this.size=size;
//  }
//
//  def setChild(child: Widget): SingleChildWidget = {
//    this.child = child
//    return this;
//  }
//
//  def getChild: Widget = child
//
//  override def draw(renderer: WidgetRenderer): Unit = {
//    if (child != null) child.draw(renderer)
//  }
//
//  override def calculateSize(maxSize: Size): Size = {
//    if (child != null) {
//      val childSize = child.calculateSize(maxSize)
//      size.set(childSize)
//    }
//    else size.set(maxSize)
//    //        System.out.println(this.getClass().getName()+" "+size.width+" "+size.height);
//    size
//  }
//
//  override def calculateLocation(parentLocation: Vector2): Vector2 = {
//    position.set(parentLocation)
//    if (child != null) child.calculateLocation(position)
//    position
//  }
//
//  override def animate(delta: Float): Unit = {
//    if (child != null) child.animate(delta)
//  }
//
//
//}
