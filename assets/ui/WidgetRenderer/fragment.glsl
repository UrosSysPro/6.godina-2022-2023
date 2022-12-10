varying vec2 v_texCoords;
//uniform sampler2D texture_0;

void main(){
    gl_FragColor=vec4(v_texCoords,0.5,1.0);
}