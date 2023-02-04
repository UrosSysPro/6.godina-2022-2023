attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

uniform mat4 view;
uniform mat4 projection;

void main(){
    gl_Position=projection*view*vec4(a_position,1.0);
}