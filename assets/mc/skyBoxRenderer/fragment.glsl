#version 130
#define PI 3.1415926538

varying vec2 v_position;
uniform mat4 rotation;

void main() {
    vec3 focusPoint = vec3(0.0, 0.0, sqrt(3.0));
    vec3 position=vec3(v_position,0.0);
    vec4 direction=rotation*vec4(position-focusPoint,1.0);
    float pitch=atan(direction.y,length(direction.xz));
    float alpha=pitch/PI+0.5;
    gl_FragColor=mix(vec4(0.7,0.3,0.3,1.0),vec4(0.6,0.7,0.9,1.0),alpha);
}