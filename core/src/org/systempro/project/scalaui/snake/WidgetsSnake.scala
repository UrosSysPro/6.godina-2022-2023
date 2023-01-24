package org.systempro.project.scalaui.snake

import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen
import org.systempro.project.scalaui.widgets._
import org.systempro.project.scalaui.{GestureDetector, Key, Scene, Widget}

import scala.util.Random


class WidgetsSnake extends BasicScreen{
  var scene:Scene=null;
  val width: Int = Gdx.graphics.getWidth/50;
  val height: Int = Gdx.graphics.getHeight/30;
  val keys: Array[Array[Key[Switch]]] =Array.ofDim[Key[Switch]](width,height);
  var parts:List[Point]=List();
  var direction:Point=Point(1,0);
  var timer:Float=0;
  val random:Random=new Random();
  val food:Point=Point(random.nextInt(width),random.nextInt(height));


  override def show(): Unit = {
    scene = new Scene(
      root = new Stack(
        children = List(
          new Container(
            color = Color.GRAY,
            child = {
              var rows: List[Widget] = List();
              for (j <- 0 until height) {
                var cells: List[Widget] = List();
                for (i <- 0 until width) {
                  keys(i)(j) = Key();
                  cells = cells :+ new Expanded(
                    child = new SizedBox(
                      width = 50,
                      height = 30,
                      child = new Switch(uncheckedColor = Color.LIGHT_GRAY).setKey(keys(i)(j))
                    )
                  )
                }
                rows = rows :+ new Expanded(
                  child = new Row(
                    children = cells
                  )
                );
              }
              new Column(children = rows);
            }
          ),
          new GestureDetector(
            touchDown = (_,_,_,_)=>true,
            keyDown = (keycode)=>{
              keycode match {
                case Input.Keys.W=>{direction.x =  0;direction.y = -1}
                case Input.Keys.S=>{direction.x =  0;direction.y =  1}
                case Input.Keys.A=>{direction.x = -1;direction.y =  0}
                case Input.Keys.D=>{direction.x =  1;direction.y =  0}
                case _=>
              }
              true;
            }
          )
        )
      )
    );
    parts=parts :+ Point(width/2,height/2);
    parts=parts :+ Point(width/2,height/2);
    parts=parts :+ Point(width/2,height/2);
    parts=parts :+ Point(width/2,height/2);
    parts=parts :+ Point(width/2,height/2);
    parts=parts :+ Point(width/2,height/2);
    Gdx.input.setInputProcessor(scene.inputProcessor);

    scene.layout();
  }

  override def render(delta: Float): Unit = {
    ScreenUtils.clear(0,0,0,1);
    timer+=delta*1000;
    if(timer>100){
      timer=0;
      updateGame();
    }

    scene.animate(delta);
    scene.draw();
  }

  def updateGame(): Unit ={
    //vrati rep na sikljuceni switch
    var x = parts.last.x;
    var y = parts.last.y;
    keys(x)(y).widget.setValue(false);
    //update parts od kraja ka pocetku
    var i = parts.length - 1;
    while (i >= 1) {
      parts(i).x = parts(i - 1).x;
      parts(i).y = parts(i - 1).y;
      i -= 1;
    }
    //update prvi part
    parts.head.x += direction.x;
    parts.head.y += direction.y;
    if (parts.head.x > width - 1) parts.head.x = 0;
    if (parts.head.y > height - 1) parts.head.y = 0;
    if (parts.head.x < 0) parts.head.x = width - 1;
    if (parts.head.y < 0) parts.head.y = height - 1;
    //upali se prvi switch
    x = parts.head.x;
    y = parts.head.y;
    keys(x)(y).widget.setValue(true);
    //update food
    keys(food.x)(food.y).widget.setValue(true);
    keys(food.x)(food.y).widget.checkedColor = Color.FIREBRICK;
    //proveri dal je pojeo
    if (parts.head.x == food.x && parts.head.y == food.y) {
      parts = parts :+ Point(width / 2, height / 2);
      keys(food.x)(food.y).widget.checkedColor = Color.LIME;
      food.x = random.nextInt(width);
      food.y = random.nextInt(height);
    }
    //proveri da li je game over
    i=1;
    while(i<parts.length){
      if(parts.head.x==parts(i).x && parts.head.y==parts(i).y){
        //game over
      }
      i+=1;
    }
  }

  override def resize(width: Int, height: Int): Unit = {
    scene.resize(width,height);
  }

  override def dispose(): Unit = {
    scene.dispose();
  }
}
