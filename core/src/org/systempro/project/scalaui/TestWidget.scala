package org.systempro.project.scalaui

import com.badlogic.gdx.graphics.Color

class TestWidget extends SingleChildWidget {
  var container:Container=null;
  setChild(new SizedBox(
    width = 100,
    height = 100,
    child = new Padding(
      child = {
        container = new Container(
          color = Color.GOLD,
          child = new GestureDetector() {
            //            override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = true;
            //
            //            override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
            //              container.color=Color.ORANGE;
            //              return true;
            //            }
            //
            //            override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
            //              val r:Float=(screenX-position.x)/size.width;
            //              val g:Float=(screenY-position.y)/size.height;
            //              val b:Float=0.5f;
            //              container.color=new Color().set(r,g,b,1f);
            //              return true;
            //            }
            //
            //            override def scrolled(amountX: Float, amountY: Float): Boolean = {
            //              container.color = Color.RED;
            //              return true;
            //            }
            override def mouseLeave(screenX: Int, screenY: Int): Boolean = {
              container.color=Color.BLACK;
              return true;
            }

            override def mouseEnter(screenX: Int, screenY: Int): Boolean = {
              container.color=Color.WHITE;
              return true;
            }

          }
        );
        container;
      },
      padding = 10
    )
  ));
}
