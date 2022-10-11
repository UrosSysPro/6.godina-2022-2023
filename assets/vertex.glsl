attribute vec2 pos;

void main(){
    //4 koordinata mora da bude 1
    gl_Position=vec4(pos,0.0,1.0);
}