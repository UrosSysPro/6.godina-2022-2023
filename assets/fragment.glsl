void main(){
    vec4 pos = gl_FragCoord;
    pos.xy/=vec2(800,600);
    gl_FragColor=vec4(pos.xy,0.5,1.0);
}