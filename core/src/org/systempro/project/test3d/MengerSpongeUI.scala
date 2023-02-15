package org.systempro.project.test3d

import com.badlogic.gdx.graphics.{Color, OrthographicCamera, PerspectiveCamera}
import org.systempro.project.basics3d.CameraController
import org.systempro.project.scalaui.{EdgeInsets, Fonts, GestureDetector, Key, Scene, Widget}
import org.systempro.project.scalaui.widgets.{Align, Alignment, Column, Container, Expanded, Padding, Row, SizedBox, Slider, Switch, Text}

object MengerSpongeUI {

  var controller:CameraController=null;

  var scene:Scene=null;
  var fpsKey=Key[Text]();
  var instanceNumKey=Key[Text]();
  var frameTimeKey=Key[Text]();
  def init(controller:CameraController): Unit = {
    this.controller=controller;
    Fonts.load()
    scene = new Scene(
      root = new Row(
        children = List(
          new Container(
            color = Color.DARK_GRAY,
            child = new GestureDetector(
              touchDown = (_, _, _, _) => true,
              child = new SizedBox(
                width = 300,
                child = new Column(
                  children = List(
                    optionSlider("fov", 60, 150, 60f, (value) => {
                      this.controller.camera match {
                        case camera: PerspectiveCamera=>{
                          camera.fieldOfView=value
                          camera.update()
                        }
                        case _=>;
                      }
                    }),
                    optionSlider("hello", 0, 1, 0.5f, (_) => Unit),
                    optionSwitch("invert",on = false, (value)=>{
                      this.controller.invertedCamera=value;
                    }),
                    optionSlider("hello", 0, 1, 0.5f, (_) => Unit),
                    infoWidget(fpsKey,"fps"),
                    infoWidget(instanceNumKey,"instances"),
                    infoWidget(frameTimeKey,"frame time")
                  )
                )
              )
            )
          ),
          new Expanded()
        )
      )
    )

    scene.layout()
  }

  def optionSlider(text:String,min:Float,max:Float,value:Float,onChange:(Float)=>Unit):Widget={
    new Padding(
      padding = EdgeInsets.symetric(vertical = 5,horizontal = 0),
      child = new SizedBox(
        height = 30,
        child = new Row(
          children = List(
            new Expanded(
              child = new Align(
                alignment=Alignment.centerRight,
                child = new Text(
                  text = text, font = Fonts.roboto, color = Color.WHITE, scale = 0.4f
                )
              )
            ),
            new SizedBox(
              width = 200,
              child = new Padding(
                padding = EdgeInsets.symetric(horizontal = 10,vertical = 7),
                child = new Slider(
                  min = min,
                  max = max,
                  value = value,
                  step = (max-min)/200f,
                  onChange = onChange,
                  roundness = 0.5f,
                  backgroundColor = Color.SLATE,
                  backgroundWidth = 0.4f,
                  foregroundWidth = 0.4f
                )
              )
            )
          )
        )
      )
    )
  }
  def optionSwitch(text:String,on:Boolean,onChange:(Boolean)=>Unit):Widget={
    new Padding(
      padding = EdgeInsets.symetric(vertical = 5, horizontal = 0),
      child = new SizedBox(
        height = 44,
        child = new Row(
          children = List(
            new Expanded(
              child = new Align(
                alignment = Alignment.centerRight,
                child = new Text(text = text, font = Fonts.roboto, color = Color.WHITE, scale = 0.4f)
              )
            ),
            new SizedBox(
              width = 200,
              child = new Padding(
                padding = EdgeInsets.symetric(vertical = 7, horizontal = 10),
                child = new SizedBox(
                  width = 50,height = 30,
                  child = new Switch(
                    value = on,
                    padding = 2,
                    onChange=onChange
                  )
                )
              )
            )
          )
        )
      )
    )
  }
  def infoWidget(key:Key[Text],info:String):Widget= {
    new Padding(
      padding = EdgeInsets.symetric(vertical = 5, horizontal = 0),
      child = new SizedBox(
        height = 30,
        child = new Row(
          children = List(
            new Expanded(
              child = new Align(
                alignment = Alignment.centerRight,
                child = new Text(text = info, font = Fonts.roboto, color = Color.WHITE, scale = 0.4f)
              )
            ),
            new SizedBox(
              width = 200,
              child = new Padding(
                padding = EdgeInsets.symetric(vertical = 7,horizontal = 10),
                child = new Text("null",font = Fonts.roboto, color = Color.WHITE, scale = 0.4f).setKey(key)
              )
            )
          )
        )
      )
    )
  }

}
