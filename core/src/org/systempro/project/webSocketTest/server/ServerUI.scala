package org.systempro.project.webSocketTest.server

import com.badlogic.gdx.graphics.Color
import org.systempro.project.scalaui.widgets.{Column, Text}
import org.systempro.project.scalaui.{Fonts, Key, Scene}


object ServerUI {
  var scene:Scene=null
  var textKeys:Array[Key[Text]]=null;

  def load():Unit={
    Fonts.load()
    val n = 30
    textKeys=new Array[Key[Text]](n);
    var widgets:List[Text]=List();
    for(i <- 0 until n ){
      textKeys(i)=Key();
      widgets=widgets:+new Text(
        font = Fonts.roboto,
        text = "",
        scale = 0.4f,
        color = Color.WHITE
      )
    }
    scene=new Scene(
      root = new Column(
        children = widgets
      )
    )
  }

}
