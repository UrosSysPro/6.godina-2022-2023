package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen

class NewRendererTest extends BasicScreen{

  var scene:Scene=null;

  override def show(): Unit = {
    scene=new Scene(
      root = new Container(
        color = Color.SKY,
        child = new Align(
          alignment = Alignment.center,
          child = new SizedBox(
            width = 400,height = 300,
            child = new Container(
              color = Color.FOREST
            )
          )
        )
      )
    );
    Gdx.input.setInputProcessor(scene.inputProcessor);
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
    scene.dispose();
  }
}
