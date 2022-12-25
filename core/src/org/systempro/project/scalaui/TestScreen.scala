package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen

class TestScreen extends BasicScreen{

  var scene:Scene=null;

  override def show(): Unit = {
    var list:List[Widget]=List();
    var i=0;
    while(i<9){
      list=list :+ new Align(
        alignment = i,
        child = new Column(
          children = List(
            red(),
            red(),
            red()
          )
        )
      );
      i+=1;
    }

    scene=new Scene(
      new Container(
        color=Color.SKY,
        child =new SizedBox(
          child =new Stack(
            children =list
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

  def kvadrat():Widget={
    new SizedBox(
      width = 100,
      height = 100,
      child =new Padding(
        child = new Container(
          color = Color.GOLD,
          child = new GestureDetector(){
            override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
              return true;
            }
          }
        ),
        padding = 10
      )
    );
  }
  def red():Widget={
    new Row(
      children = List(
        kvadrat(),
        kvadrat(),
        kvadrat()
      )
    )
  }

}
