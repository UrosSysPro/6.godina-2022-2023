package org.systempro.project.scalaui.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.systempro.project.scalaui.{EdgeInsets, GestureDetector, WidgetRenderer}
import org.systempro.project.ui.Size

class TextField(
               var lines:Int=1,
               var maxWidth:Float=200,
               var padding:EdgeInsets=EdgeInsets.symetric(10,15),
               var placeholder:String="",
               var text:String="",
               var backgroundColor:Color=Color.WHITE,
               var borderColor:Color=Color.DARK_GRAY,
               var roundness:Float=0,
               var borderWidth:Float=2,
               var font:BitmapFont,
               var textColor:Color=Color.BLACK,
               var placeholderColor:Color=Color.DARK_GRAY,
               var scale:Float=1
               ) extends GestureDetector{
  override def draw(renderer: WidgetRenderer): Unit = {
    var x: Float = 0;
    var y: Float = 0;
    val availableWidth=size.width-padding.left-padding.right;

    renderer.rect(position.x,position.y,size.width,size.height,backgroundColor,borderColor,size.height*roundness,borderWidth,1);

    val displayedString=if(text.isEmpty)placeholder else text;
    val color=if(text.isEmpty)placeholderColor else textColor;

    for (c <- displayedString) {
      val glyph = font.getData.getGlyph(c);
      if(glyph!=null){
        renderer.glyph(
          glyph, color,
          x + padding.left + position.x + glyph.xoffset * scale,
          y + padding.top + position.y + glyph.yoffset * scale,
          glyph.width * scale,
          glyph.height * scale
        );
        if (x + glyph.xadvance * scale < availableWidth) {
          x += glyph.xadvance * scale;
        } else {
          x = 0;
          y += font.getLineHeight * scale;
        }
      }
    }
  }

  override def calculateSize(maxSize: Size): Size = {
    size.set(Math.min(maxWidth,maxSize.width),font.getLineHeight*scale*lines+padding.top+padding.bottom);
  }

  touchDown=(_,_,_,_)=>true;
  keyTyped=(character)=>{
    if(character==8 && text.nonEmpty)text=text.substring(0,text.length-1);
    if(character>=32)text+=character;
    true
  }

}
