package org.systempro.project.scalaui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import org.systempro.project.BasicScreen
import org.systempro.project.scalaui.widgets.{Align, Alignment, Column, Container, Expanded, Padding, Row, SizedBox, Stack, Visible}

class TestScreen extends BasicScreen{

  var scene:Scene=null;
  var scaffold:Widget=null;
  var alertDiaolog:Visible=null;
  var drawer:Visible=null;

  override def show(): Unit = {
    scene = new Scene(
      root = new Container(
        color = Color.valueOf("515151"),
        child = new Stack(
          children = List(
            /*scaffold*/ {
              scaffold = new Column(
                children = List(
                  /*app bar*/ new SizedBox(
                    height = 70,
                    child = new Container(
                      color = Color.valueOf("707070"),
                      child = new Row(
                        children = List(
                          new SizedBox(
                            width = 70,
                            child = new Padding(padding = EdgeInsets.all(10), child = new Container(color = Color.valueOf("515151"),
                              child = new GestureDetector(touchDown = (screnX, screenY, pointer, button) => {
                                drawer.visible = true;
                                true;
                              })
                            ))
                          ),
                          new Expanded(),
                          new SizedBox(
                            width = 70,
                            child = new Padding(padding = EdgeInsets.all(10), child = new Container(color = Color.valueOf("515151")))
                          ),
                          new SizedBox(
                            width = 70,
                            child = new Padding(padding = EdgeInsets.all(10), child = new Container(color = Color.valueOf("515151")))
                          ),
                          new SizedBox(
                            width = 70,
                            child = new Padding(padding = EdgeInsets.all(10), child = new Container(color = Color.valueOf("515151")))
                          )
                        )
                      )
                    )
                  ),
                  /*body*/ new Align(
                    alignment = Alignment.center,
                    child = new SizedBox(
                      width = 100,
                      height = 100,
                      child = new Container(color = Color.SLATE,
                        child = new GestureDetector(touchDown = (screenX, screenY, pointer, button) => {
                          alertDiaolog.visible = true;
                          true;
                        })
                      )
                    )
                  )
                )
              );
              scaffold
            },
            /*drawer*/ {
              drawer = new Visible(
                child = new GestureDetector(
                  touchDown = (_, _, _, _) => {
                    drawer.visible = false;
                    true
                  },
                  child = new Container(
                    color = new Color().set(0, 0, 0, 0.3f),
                    child = new Align(
                      alignment = Alignment.centerLeft,
                      child = new Padding(
                        padding = EdgeInsets.all(20),

                        child = new GestureDetector(
                          touchDown = (_, _, _, _) => true,
                          child = new Container(
                            color = Color.WHITE,
                            child = new SizedBox(
                              width = 400,
                              child = new Padding(
                                padding = EdgeInsets.all(20),
                                child = new Column(
                                  children = List(
                                    new SizedBox(height = 100),
                                    new SizedBox(
                                      width = 70, height = 70,
                                      child = new Container(color = Color.DARK_GRAY)
                                    ),
                                    new SizedBox(height = 15),
                                    new SizedBox(
                                      width = 100, height = 20,
                                      child = new Container(color = Color.LIGHT_GRAY)
                                    ),
                                    new SizedBox(height = 15),
                                    new SizedBox(
                                      width = 120, height = 20,
                                      child = new Container(color = Color.LIGHT_GRAY)
                                    ),
                                    new SizedBox(height = 15),
                                    new SizedBox(
                                      width = 80, height = 20,
                                      child = new Container(color = Color.LIGHT_GRAY)
                                    ),
                                    new SizedBox(height = 15),
                                    new SizedBox(
                                      width = 150, height = 20,
                                      child = new Container(color = Color.LIGHT_GRAY)
                                    )
                                  )
                                )
                              )
                            )
                          ))
                      )
                    )
                  )
                ),
                visible = false
              );
              drawer
            },
            /*alert dialog*/ {
              alertDiaolog = new Visible(
                visible=false,
                child = new GestureDetector(
                  touchDown = (_, _, _, _) => {
                    alertDiaolog.visible = false;
                    true;
                  },
                  child = new Container(
                    color = new Color().set(0, 0, 0, 0.3f),
                    child = new Align(
                      alignment = Alignment.center,
                      child = new SizedBox(
                        width = 300,
                        height = 250,
                        child = new GestureDetector(child = new Container(
                          color = Color.WHITE,
                          child = new Padding(
                            padding = EdgeInsets.all(10),
                            child = new Column(
                              children = List(
                                new SizedBox(height = 20, width = 200, child = new Container(color = Color.DARK_GRAY)),
                                new SizedBox(height = 10),
                                new SizedBox(height = 20, width = 150, child = new Container(color = Color.DARK_GRAY)),
                                new SizedBox(height = 10),
                                new SizedBox(height = 20, width = 170, child = new Container(color = Color.DARK_GRAY)),
                                new Expanded(),
                                new Align(
                                  expandVertical = false,
                                  alignment = Alignment.centerRight,
                                  child = new GestureDetector(
                                    touchDown = (_, _, _, _) => {
                                      alertDiaolog.visible = false;
                                      true;
                                    },
                                    child = new SizedBox(width = 60, height = 30, child = new Container(color = Color.SKY))
                                  )
                                )
                              )
                            )
                          )
                        ), touchDown = (_, _, _, _) => true
                        )
                      )
                    )
                  )
                )
              );
              alertDiaolog
            }
          )
        )
      )
    );
    Gdx.input.setInputProcessor(scene.inputProcessor);
  }

  override def render(delta: Float): Unit = {
    ScreenUtils.clear(0,0,0,1);
    Gdx.graphics.setTitle(""+Gdx.graphics.getFramesPerSecond)
    scene.layout();
    scene.draw();
  }

  override def resize(width: Int, height: Int): Unit = {
    scene.resize(width,height);
  }

}
