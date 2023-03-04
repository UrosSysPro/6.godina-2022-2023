#version 330

attribute vec3 a_position;

attribute vec4 col0;
attribute vec4 col1;
attribute vec4 col2;
attribute vec4 col3;

uniform mat4 view;
uniform mat4 projection;

varying vec3 v_viewPosition;

void main(){
    mat4 model=mat4(col0,col1,col2,col3);
    vec4 position=projection*view*model*vec4(a_position,1.0);

    v_viewPosition=position.xyz;

    gl_Position=position;
}