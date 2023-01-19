package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen
import org.systempro.project.scalaui.widgets._

class TextWidgetTest extends BasicScreen{

  var scene:Scene=null;
  val textFieldKey:Key=Key();

  override def show(): Unit = {
    Fonts.load();
    scene = new Scene(
      root = new Container(
        color = Color.SKY,
        child = new Align(
          new Container(
            blur = 1,
            borderColor = Color.WHITE,
            color = Color.WHITE,
            borderRadius = 20,
            child = new SizedBox(
              width = 400,
              height = 300,
              child = new Align(
                alignment = Alignment.center,
                child = new Column(
                  children = List(
                    new TextField(
                      font = Fonts.roboto,
                      placeholder = "placeholder",
                      scale = 0.4f,
                      lines = 2,
                      roundness = 0.3f
                    ).setKey(textFieldKey),
                    new SizedBox(
                      width = 200,
                      height=20,
                      child = new Slider(
                        backgroundWidth = 0.3f,
                        foregroundWidth = 0.3f,
                        min = 0.2f,
                        max=2f,
                        step = 0.01f,
                        backgroundColor = Color.DARK_GRAY,
                        foregroundColor = Color.SKY,
                        circleColor = Color.LIGHT_GRAY,
                        roundness=0.5f,
                        onChange = (value)=>{
                          textFieldKey.widget match {
                            case textField: TextField=>textField.scale=value
                            case _=>;
                          }
                        }
                      )
                    ),
                    new SizedBox(
                      width = 50,
                      height = 30,
                      child = new Switch(padding = 2)
                    )
                  )
                )
              )
            )
          )
        )
      )
    )
    Gdx.input.setInputProcessor(scene.inputProcessor);
  }

  override def render(delta: Float): Unit = {
    ScreenUtils.clear(0,0,0,1);
    scene.layout();
    scene.animate(delta);
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
