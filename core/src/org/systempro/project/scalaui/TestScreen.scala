package org.systempro.project.scalaui

import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen

class TestScreen extends BasicScreen{

  var scene:Scene=null;
  var alignment: Int =Alignment.center;
  var align:Align=null;

  override def show(): Unit = {
    scene=new Scene(
      new Container(
        child =new SizedBox(
          child = {align=new Align(
            alignment=alignment,
            child = new SizedBox(
              width=100,
              height=100,
              child = new Container()
            )
          );align}
        )
      )
    );
  }

  override def render(delta: Float): Unit = {
    ScreenUtils.clear(0,0,0,1);
    if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))alignment-=1;
    if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))alignment+=1;
    if(Gdx.input.isKeyJustPressed(Input.Keys.UP))alignment-=3;
    if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))alignment+=3;
    align.alignment=alignment;
    scene.layout();
    scene.draw();
  }

  override def resize(width: Int, height: Int): Unit = {
    scene.resize(width,height);
  }

}
