package org.systempro.project.renderers

import com.badlogic.gdx.graphics.Color
import org.systempro.project.scalaui.{EdgeInsets, Fonts, Key, Scene}
import org.systempro.project.scalaui.widgets.{Align, Alignment, Padding, Row, SizedBox, Slider, Text}

object ShadowTestUI {
  var scene:Scene = null;
  var value:Float = 0;
  val key:Key[Text] = Key[Text]();
  def init(): Unit = {
    scene = new Scene(
      root = new Align(
        alignment = Alignment.bottomLeft,
        child = new Padding(
          padding = EdgeInsets.all(20),
          child = new Row(
            children = List(
              new Text(text = "hello", font = Fonts.roboto, color = Color.WHITE, scale = 0.5f).setKey(key),
              new SizedBox(
                width=200,
                height=30,
                child = new Slider(
                  foregroundColor = Color.SKY,
                  min = 1, max = 100, step = 0.1f, value = 10,
                  roundness=0.5f,
                  onChange = value => {
                    this.value = value;
                  }
                )
              )
            )
          )
        )
      )
    )
  }
}
