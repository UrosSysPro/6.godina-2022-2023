package org.systempro.project.test3d

import com.badlogic.gdx.graphics.Color
import org.systempro.project.scalaui.{Key, Scene}
import org.systempro.project.scalaui.widgets.{Container, Expanded, Row, SizedBox}

object MengerSpongeUI {

  var key:Key[Container]=Key();

  var scene:Scene=null;

  def init(): Unit ={
    scene=new Scene(
      root = new Row(
        children = List(
          new Expanded(),
          new SizedBox(
            width = 300,
            child = new Container(
              color = Color.DARK_GRAY
            ).setKey(key)
          )
        )
      )
    )

    scene.layout()
    println(s"${key.widget.size.width} ${key.widget.size.height}")
  }

}
