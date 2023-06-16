
attribute vec2 position;
attribute float strana;
attribute vec2 texCoords;
attribute vec3 worldPosition;

uniform mat4 view;
uniform mat4 projection;
uniform sampler2D texture0;
uniform float textureWidth;
uniform float textureHeight;

varying vec2 color;
varying vec2 v_texCoord;
varying vec3 v_normal;
varying vec3 v_position;

void main(){
    initRotations();

    vec2 pos=position+0.5;
    pos.y=1.0-pos.y;

    v_texCoord=(texCoords*16.0+pos*16.0)/320.0;
//    vec4 normal=rotacije[int(strana)]*vec4(0.0,0.0,1.0,1.0);
//    v_normal=normal.xyz;
    v_normal=mat3(rotacije[int(strana)])*vec3(0.0,0.0,1.0);

    color=position*0.5+0.5;
    vec4 finalPostion=vec4(position,0.0,1.0);
    finalPostion=rotacije[int(strana)]*finalPostion;
    finalPostion+=vec4(worldPosition,0.0);
    gl_Position=projection*view*finalPostion;
    v_position=finalPostion.xyz;
}