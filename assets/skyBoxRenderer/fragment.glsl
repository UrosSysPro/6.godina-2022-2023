#define PI 3.1415926538

varying vec2 v_position;
uniform vec3 direction;

void main(){

    float alpha=atan(v_position.y,sqrt(3.0));
    float beta=atan(direction.y,length(vec2(direction.x,direction.z)));
    float angle=alpha+beta;
    float intensity=clamp(1.0,0.0,angle/(PI*5.0/12.0)/2.0);
    vec3 color=mix(vec3(0.2,0.2,0.2),vec3(0.1,0.3,0.7),intensity);
    gl_FragColor=vec4(color,1.0);
}