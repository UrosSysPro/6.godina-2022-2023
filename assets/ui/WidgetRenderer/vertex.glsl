attribute vec2 pos;
attribute vec2 texCoords;
attribute vec4 color;

varying vec2 v_texCoords;
varying vec4 v_color;
//uniform sampler2D texture_0;
uniform mat3 combined;

void main(){
    v_color=color;
    v_texCoords=texCoords;
    vec3 position=vec3(pos,1.0);
    gl_Position=vec4(combined*position,1.0);
}