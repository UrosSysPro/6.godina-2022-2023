package org.systempro.project.verlet2d

import com.badlogic.gdx.graphics.Color
import org.systempro.project.scalaui.widgets.{Column, Padding, Text}
import org.systempro.project.scalaui.{EdgeInsets, Fonts, Key, Scene, Widget}

object TestScreenUI {
  var scene:Scene=null
  var physicsTime:Key[Text]=Key();
  var drawTime:Key[Text]=Key();
  var particles:Key[Text]=Key();
  var ui:Key[Text]=Key();

  def init(): Unit ={
    Fonts.load();
    scene=new Scene(
      root = new Padding(
        padding = EdgeInsets.all(20),
        child = new Column(
          children = List(
            text(physicsTime),
            text(drawTime),
            text(particles),
            text(ui)
          )
        )
      )
    )
  }
  def text(key:Key[Text]):Widget={
    new Text(
      text = "0.0",
      font = Fonts.roboto,
      color = Color.PURPLE,
      scale = 0.5f
    ).setKey(key)
  }
}
