package org.systempro.project.verlet2d.examples

import com.badlogic.gdx.graphics.Color
import org.systempro.project.scalaui.{EdgeInsets, Fonts, Key, Scene}
import org.systempro.project.scalaui.widgets.{Column, Padding, Text}

object InfoUI {
  var scene:Scene=null;
  var keys:Array[Key[Text]]=Array(
    Key(),Key(),Key()
  );

  def load(): Unit = {
    Fonts.load();
    scene = new Scene(
      root = new Padding(
        padding=EdgeInsets.all(20),
        child = new Column(
          children = List(
            text("placeholder").setKey(keys(0)),
            text("placeholder").setKey(keys(1)),
            text("placeholder").setKey(keys(2))
          )
        )
      )
    )
  }

  def text(value:String):Text=
    new Text(
      font=Fonts.roboto,
      text = value,
      scale=0.5f,
      color = Color.PURPLE
    )
}
