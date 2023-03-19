varying vec4 v_viewPosition;

uniform float near;
uniform float far;

void main(){
    float depth=v_viewPosition.z/v_viewPosition.w*0.5+0.5;
//    float depth=(v_viewPosition.z-near)/(far-near);
    gl_FragColor=vec4(depth);
}