package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen
import org.systempro.project.scalaui.widgets.{Align, Container, SizedBox, Text}

class TextWidgetTest extends BasicScreen{

  var scene:Scene=null;

  override def show(): Unit = {
    Fonts.load();
    scene=new Scene(
      root = new Container(
        color = Color.SKY,
        child = new Align(
          child = new Text(
            font = Fonts.roboto,
            text = {
              var string="";
              for(i<- 0 to 20)string+="hello";
              string;
            },
            color = Color.LIME
          )
        )
      )
    )
    Gdx.input.setInputProcessor(scene.inputProcessor);
    scene.renderer.font=Fonts.roboto.getRegion.getTexture;
  }

  override def render(delta: Float): Unit = {
    ScreenUtils.clear(0,0,0,1);
    scene.layout();
    scene.draw();
  }

  override def resize(width: Int, height: Int): Unit = {
    scene.resize(width,height);
  }

  override def dispose(): Unit = {
    Fonts.dispose();
    scene.dispose();
  }
}
