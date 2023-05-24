varying vec2 v_position;
uniform vec3 direction;
void main(){
    gl_FragColor=vec4(vec3(asin(direction.y)),1.0);
}