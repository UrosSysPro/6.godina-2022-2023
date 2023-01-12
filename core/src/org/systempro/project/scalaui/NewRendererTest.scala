package org.systempro.project.scalaui

import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen

class NewRendererTest extends BasicScreen{

  var scene:Scene=null;
  var containers:List[Container]=List();

  override def show(): Unit = {
    scene=new Scene(
      root = new GestureDetector(
        child=new Container(
        color = Color.SKY,
        child = new Align(
          alignment = Alignment.center,
          child = new SizedBox(
            width = 200,height = 100,
            child = new Row(
              children = {
                var list=List[Widget]();
                for(i<-0 until 2){
                  val container=new Container(
                    color = Color.CLEAR,
                    borderColor = Color.FIREBRICK,
                    borderWidth = 20,
                    borderRadius = 20
                  )
                  containers=containers:+container;
                  list=list:+new Expanded(child = container)
                }
                list
              }
            )
          )
        )
      )
      ){
        override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = true;
        override def keyDown(keycode: Int): Boolean = {
          for (container <- containers) {
            if (keycode == Input.Keys.W) container.borderWidth += 1;
            if (keycode == Input.Keys.S) container.borderWidth -= 1;
            if (keycode == Input.Keys.A) container.blur += 1;
            if (keycode == Input.Keys.D) container.blur -= 1;
            if (keycode == Input.Keys.Q) container.borderRadius += 1;
            if (keycode == Input.Keys.E) container.borderRadius -= 1;
          }
          return true;
        }
      }
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
