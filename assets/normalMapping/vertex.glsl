#version 330

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;
attribute vec4 a_tangent;

attribute vec4 col0;
attribute vec4 col1;
attribute vec4 col2;
attribute vec4 col3;

uniform mat4 view;
uniform mat4 projection;

varying vec2 v_texCoord0;
varying vec3 v_normal;
varying vec3 v_position;
varying vec4 v_tangent;

void main(){
    mat4 model=mat4(col0,col1,col2,col3);
    mat3 normalTransform=inverse(transpose(mat3(model)));

    vec4 pos=model*vec4(a_position,1.0);
    v_position=pos.xyz;
    v_normal=normalTransform*a_normal;
    v_texCoord0=a_texCoord0;
//    v_tangent=a_tangent;
    v_tangent=model*a_tangent;
    gl_Position=projection*view*model*vec4(a_position,1.0);
}