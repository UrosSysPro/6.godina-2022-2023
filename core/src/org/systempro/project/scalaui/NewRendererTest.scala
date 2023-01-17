package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen
import org.systempro.project.camera.Camera2d

class NewRendererTest extends BasicScreen{

  var renderer:WidgetRenderer=null;
  var camera:Camera2d=null;
  var font:BitmapFont=null;
  override def show(): Unit = {
    renderer= new WidgetRenderer();
    camera=new Camera2d();
    val width=Gdx.graphics.getWidth;
    val height=Gdx.graphics.getHeight;
    camera.setScale(1,-1);
    camera.setSize(width,height);
    camera.setTranslation(-width/2,-height/2);
    camera.update();

    renderer.camera2d=camera;

    val generator=new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
    val parameter=new FreeTypeFontParameter();
    parameter.color=Color.WHITE;
    parameter.size=40;
    parameter.flip=true;
    font=generator.generateFont(parameter);
    generator.dispose();

    renderer.font=font.getRegion().getTexture;
  }

  override def render(delta: Float): Unit = {
    ScreenUtils.clear(0,0,0,1);
//    renderer.rect(0,0,100,100,Color.CLEAR,Color.SKY,40,10,10);
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);

    val rec="hello world +=-+ AWA IMIV";
    var x:Float=0;
    val scale:Float=(Gdx.input.getX().toFloat-300)/Gdx.graphics.getWidth.toFloat*3;
    for(c<-rec){
      val glyph = font.getData.getGlyph(c);
//      renderer.verticalGradient(Color.CORAL, Color.SKY, -padding, -padding, glyph.width * 4 + 2 * padding, glyph.height * 4 + 2 * padding, 10, 1);
      renderer.glyph(glyph, Color.LIME, glyph.xoffset+x,glyph.yoffset, glyph.width , glyph.height );
       x += glyph.xadvance.toFloat;
    }
    renderer.flush();
  }

  override def dispose(): Unit = {
    renderer.dispose();
  }

  override def resize(width: Int, height: Int): Unit = {
    camera.setSize(width,height);
    camera.update()
  }
}
