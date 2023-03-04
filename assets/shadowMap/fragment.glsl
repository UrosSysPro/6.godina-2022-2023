varying vec3 v_viewPosition;

uniform float near;
uniform float far;

void main(){
    float depth=(v_viewPosition.z-near)/(far-near);
    gl_FragColor=vec4(depth);
}