attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

uniform mat4 view;
uniform mat4 projection;

varying vec2 v_texCoord0;
varying vec3 v_normal;
varying vec3 v_position;

void main(){
    v_position=a_position;
    v_normal=a_normal;
    v_texCoord0=a_texCoord0;
    gl_Position=projection*view*vec4(a_position,1.0);
}