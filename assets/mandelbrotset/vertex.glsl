attribute vec2 pos;
varying vec2 v_pos;

void main() {
    v_pos=pos;
    gl_Position=vec4(pos,0.0,1.0);
}
