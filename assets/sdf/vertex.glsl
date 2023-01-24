attribute vec2 pos;
varying vec2 v_pos;
uniform mat4 camera;

void main(){
    v_pos=pos;
    gl_Position=camera*vec4(pos,0.0,1.0);
}