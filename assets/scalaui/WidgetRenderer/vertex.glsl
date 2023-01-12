attribute vec2 pos;
attribute vec2 texCoords;
attribute vec4 color;
attribute vec4 borderColor;
attribute float borderRadius;
attribute float borderWidth;
attribute float blur;
attribute vec2 size;

varying vec2 v_texCoords;
varying vec4 v_color;
varying vec4 v_borderColor;
varying float v_borderRadius;
varying float v_borderWidth;
varying float v_blur;
varying vec2 v_size;
//uniform sampler2D texture_0;
uniform mat3 combined;

void main(){
    v_color=color;
    v_texCoords=texCoords;
    v_borderColor=borderColor;
    v_borderRadius=borderRadius;
    v_borderWidth=borderWidth;
    v_blur=blur;
    v_size=size;
    vec3 position=vec3(pos,1.0);
    gl_Position=vec4(combined*position,1.0);
}