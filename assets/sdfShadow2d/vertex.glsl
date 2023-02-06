attribute vec2 pos;
varying vec2 v_pos;
uniform mat4 camera;
uniform vec2 renderSize;
uniform vec2 worldSize;

void main(){
    v_pos=pos*worldSize/renderSize;
    gl_Position=camera*vec4(pos,0.0,1.0);
}