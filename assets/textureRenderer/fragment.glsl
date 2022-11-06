varying vec2 v_texCoords;
uniform sampler2D texture_0;

void main(){
    gl_FragColor=texture2D(texture_0,v_texCoords);
}