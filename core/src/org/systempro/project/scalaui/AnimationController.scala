package org.systempro.project.scalaui

class AnimationController(
                           var value:Float=0,
                           var duration:Float=1000,
                           var state:Int=0,
                           var onChange:Float=>Unit=_=>{},
                           var onStateChange:Int=>Unit=_=>{}
                         ) {
  def update(delta:Float): Unit ={
    if(state!=AnimationController.running&&state!=AnimationController.runningReverse)return ;
    val change=delta*1000/duration;
    value+=(if(state==AnimationController.running)change else -change);
    if(state==AnimationController.running&&value>=1){
      state=AnimationController.end;
      value=1;
      onStateChange(state)
    }
    if (state == AnimationController.runningReverse && value <=0) {
      state = AnimationController.start;
      value=0;
      onStateChange(state);
    }
    onChange(value);
  }
}

object AnimationController{
  val start=0;
  val running=1;
  val runningReverse=2;
  val end=3;
  val paused=4;
}
