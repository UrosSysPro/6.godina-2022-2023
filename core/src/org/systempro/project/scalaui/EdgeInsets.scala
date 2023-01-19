package org.systempro.project.scalaui

class EdgeInsets(var top:Float=0,var left:Float=0,var bottom:Float=0,var right:Float=0) {


}

object EdgeInsets{
  def all(padding:Float):EdgeInsets={
    new EdgeInsets(padding,padding,padding,padding);
  }
  def symetric(vertical:Float,horizontal:Float):EdgeInsets={
    new EdgeInsets(vertical,horizontal,vertical,horizontal);
  }
}
