package org.systempro.project.sdf

import org.systempro.project.scalaui.{EdgeInsets, Fonts, Key, Scene}
import org.systempro.project.scalaui.widgets.{Align, Alignment, Padding, Text}

object SceneLoader {
  def load(key:Key[Text]):Scene={
    Fonts.load();
    new Scene(
      root = new Align(
        alignment = Alignment.topCenter,
        child = new Padding(
          padding = new EdgeInsets( top = 20 ),
          child = new Text(
            font = Fonts.roboto,
            text = ""
          ).setKey(key)
        )
      )
    )
  }
}
