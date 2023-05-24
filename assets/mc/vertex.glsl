#define PI 3.1415926538

mat4 rotationMatrix(vec3 axis, float angle) {
    axis = normalize(axis);
    float s = sin(angle);
    float c = cos(angle);
    float oc = 1.0 - c;

    return mat4(oc * axis.x * axis.x + c,           oc * axis.x * axis.y - axis.z * s,  oc * axis.z * axis.x + axis.y * s,  0.0,
                oc * axis.x * axis.y + axis.z * s,  oc * axis.y * axis.y + c,           oc * axis.y * axis.z - axis.x * s,  0.0,
                oc * axis.z * axis.x - axis.y * s,  oc * axis.y * axis.z + axis.x * s,  oc * axis.z * axis.z + c,           0.0,
                0.0,                                0.0,                                0.0,                                1.0);
}

vec3 rotate(vec3 v, vec3 axis, float angle) {
    mat4 m = rotationMatrix(axis, angle);
    return (m * vec4(v, 1.0)).xyz;
}

mat4 translationMatrix(vec3 delta) {
    return mat4(
        vec4(1.0, 0.0, 0.0, 0.0),
        vec4(0.0, 1.0, 0.0, 0.0),
        vec4(0.0, 0.0, 1.0, 0.0),
        vec4(delta, 1.0));
}
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

mat4[6] rotacije;

void initRotations(){
    //0 prednja strana
    //1 zadnja strana
    //2 desna strana
    //3 leva strana
    //4 gore
    //5 dole
    rotacije[0]=translationMatrix(vec3( 0.0, 0.0, 0.5));//*rotationMatrix(vec3(0.0,1.0,0.0),  0.0);
    rotacije[1]=translationMatrix(vec3( 0.0, 0.0,-0.5))*rotationMatrix(vec3(0.0,1.0,0.0),  PI    );
    rotacije[2]=translationMatrix(vec3( 0.5, 0.0, 0.0))*rotationMatrix(vec3(0.0,1.0,0.0), -PI/2.0);
    rotacije[3]=translationMatrix(vec3(-0.5, 0.0, 0.0))*rotationMatrix(vec3(0.0,1.0,0.0),  PI/2.0);
    rotacije[4]=translationMatrix(vec3( 0.0, 0.5, 0.0))*rotationMatrix(vec3(1.0,0.0,0.0),  PI/2.0);
    rotacije[5]=translationMatrix(vec3( 0.0,-0.5, 0.0))*rotationMatrix(vec3(1.0,0.0,0.0), -PI/2.0);
}

void main(){
    initRotations();

    vec2 pos=position+0.5;
    pos.y=1.0-pos.y;

    v_texCoord=(texCoords*16.0+pos*16.0)/320.0;

    color=position*0.5+0.5;
    vec4 finalPostion=vec4(position,0.0,1.0);
    finalPostion=rotacije[int(strana)]*finalPostion;
    finalPostion+=vec4(worldPosition,0.0);
    gl_Position=projection*view*finalPostion;
}