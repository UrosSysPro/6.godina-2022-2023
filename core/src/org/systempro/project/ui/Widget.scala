//package org.systempro.project.ui
//
//import com.badlogic.gdx.math.Vector2
//
//import scala.beans.BeanProperty
//
//abstract class Widget() {
//  var position:Vector2=null;
//  var size:Size=null;
//
//  def this(position:Vector2,size:Size)={
//    this();
//    this.position=position;
//    this.size=size;
//  }
//
//  def draw(renderer: WidgetRenderer): Unit
//
//  def calculateSize(maxSize: Size): Size
//
//  def calculateLocation(parentLocation: Vector2): Vector2
//
//  def animate(delta: Float): Unit
//}
