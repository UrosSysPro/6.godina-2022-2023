package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import org.systempro.project.scalaui.{GestureDetector, Widget, WidgetRenderer}
import org.systempro.project.ui.Size

import scala.collection.mutable

class Text(var text:String,var font: BitmapFont,var color:Color=Color.WHITE) extends Widget{
  override def draw(renderer: WidgetRenderer): Unit = {
    var x:Float=0;
    var y:Float=0;
    for(c<-text){
      val glyph=font.getData.getGlyph(c);
      renderer.glyph(glyph,color,x+glyph.xoffset+position.x,y+glyph.yoffset+position.y,glyph.width,glyph.height);
      if(x+glyph.xadvance<size.width){
        x+=glyph.xadvance;
      }else{
        x=position.x;
        y+=font.getLineHeight;
      }
    }
//    renderer.border(Color.ORANGE,position.x,position.y,size.width,size.height,5,0,0)
  }

  override def calculateSize(maxSize: Size): Size = {
    var maxWidth:Float=0;
    var width:Float=0;
    var height:Float=0;

    for(c<-text){
      val glyph=font.getData.getGlyph(c);
      if(width+glyph.xadvance<maxSize.width){
        width+=glyph.xadvance;
      }else{
        maxWidth=Math.max(width,maxWidth);
        width=glyph.xadvance;
        height+=font.getLineHeight;
      }
    }
    height+=font.getLineHeight;
    maxWidth=Math.max(width,maxWidth);
    size.set(maxWidth,height);
  }

  override def calculateLocation(parentLocation: Vector2): Vector2 = {
    position.set(parentLocation);
  }

  override def animate(delta: Float): Unit = {}

  override def addListenersToStack(stack: mutable.Stack[Widget], mousePos: Vector2): Unit = {}

  override def addAllListenersToStack(stack: mutable.Stack[GestureDetector]): Unit = {}
}
