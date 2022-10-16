varying v_pos;

void main() {
    vec4 color=vec4(v_pos,0.5,1.0);
    color.rg+=vec2(1.0);
    color.rg/=2.0;
    gl_FragColor=vec4(1.0);
}
