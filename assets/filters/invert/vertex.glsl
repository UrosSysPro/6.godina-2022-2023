attribute vec2 pos;
varying vec2 v_pos;
uniform mat4 camera;

void main(){
    v_pos=(pos+1.0)/2.0;
    gl_Position=camera*vec4(pos,0.0,1.0);
}