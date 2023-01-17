package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen
import org.systempro.project.scalaui.widgets.{Column, Container, Expanded, Padding, Row}


class UIBench extends BasicScreen{

  var scene:Scene=null;

  override def show(): Unit = {
    val n=200;
    var columnList=List[Widget]();
    for(j <- 0 until n){
      var rowList=List[Widget]();
      for(i <- 0 until n) {
        val container=new Container(color = Color.LIME);
        rowList = rowList :+ new Expanded(
          child =  new GestureDetector(
            child = container,
            mouseEnter = (_,_)=>{container.color=Color.WHITE;true},
            mouseLeave = (_,_)=>{container.color=Color.LIME;true}
          )
        )
      }

      val row=new Row(children = rowList);
      columnList=columnList:+new Expanded(child = row);
    }

    val container= new Container(
      color = Color.SKY,
      child = new Column(children = columnList)
    );
    scene=new Scene(
      root = container
    )
    Gdx.input.setInputProcessor(scene.inputProcessor);
  }

  override def render(delta: Float): Unit = {
    ScreenUtils.clear(0,0,0,1);
    val start=System.currentTimeMillis();
    scene.layout();
    val layout=System.currentTimeMillis();
    scene.draw();
    val draw=System.currentTimeMillis();
    Gdx.graphics.setTitle(s"layout:${layout-start} draw:${draw-layout} fps:${Gdx.graphics.getFramesPerSecond}");
  }

  override def resize(width: Int, height: Int): Unit = {
    scene.resize(width,height);
  }

  override def dispose(): Unit = {
    scene.dispose();
  }
}
