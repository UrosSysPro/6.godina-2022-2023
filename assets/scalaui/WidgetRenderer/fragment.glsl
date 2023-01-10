varying vec2 v_texCoords;
varying vec4 v_color;
varying vec4 v_borderColor;
varying float v_borderRadius;
varying float v_borderWidth;
varying vec2 v_size;

float rectSdf(vec2 pos,float radius){
    return 0;
}

void main(){
    vec2 uv=v_texCoords-0.5*v_size;

    gl_FragColor=v_color;
}