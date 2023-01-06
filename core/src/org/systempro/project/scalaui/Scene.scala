package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import org.systempro.project.camera.Camera2d
import org.systempro.project.ui.{Size, WidgetRenderer}

class Scene {
  var renderer:WidgetRenderer = null
  var camera2d:Camera2d = null
  var root:Widget = null
  var inputProcessor:SceneInputProcessor=null;
  var focused:GestureDetector=null;

  def this(root: Widget) {
    this();
    inputProcessor=new SceneInputProcessor(this);
    this.root = root;
    renderer = new WidgetRenderer
    camera2d = new Camera2d
    val width = Gdx.graphics.getWidth
    val height = Gdx.graphics.getHeight
    resize(width, height)
    renderer.camera2d = camera2d
  }

  def layout(): Unit = {
    val width = Gdx.graphics.getWidth
    val height = Gdx.graphics.getHeight
    root.calculateSize(new Size(width, height))
    root.calculateLocation(new Vector2(0, 0))
  }

  def draw(): Unit = {
    root.draw(renderer)
    renderer.flush()
  }

  def resize(width: Float, height: Float): Unit = {
    camera2d.setSize(width, height)
    camera2d.setTranslation(-width / 2, -height / 2)
    camera2d.setScale(1, -1)
    camera2d.update()
  }
}
