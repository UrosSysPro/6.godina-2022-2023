package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen


class UIBench extends BasicScreen{

  var scene:Scene=null;

  override def show(): Unit = {
    val n=100;
    var columnList=List[Widget]();
    for(j <- 0 until n){
      var rowList=List[Widget]();
      for(i <- 0 until n) {
        rowList = rowList :+ new Expanded(
          child = new Padding(
            padding = 1,
            child = new Container(color = Color.LIME)
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
    println(container.size.width+" "+container.size.height);
    println(Gdx.graphics.getWidth+" "+Gdx.graphics.getHeight);
    Gdx.input.setInputProcessor(scene.inputProcessor);
  }

  override def render(delta: Float): Unit = {
    ScreenUtils.clear(0,0,0,1);
    Gdx.graphics.setTitle(""+Gdx.graphics.getFramesPerSecond);
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
