varying vec2 color;
varying vec2 v_texCoord;
uniform sampler2D texture0;

void main(){
//    gl_FragColor=vec4(color,0.5,1.0);
    gl_FragColor=texture2D(texture0,v_texCoord);
}