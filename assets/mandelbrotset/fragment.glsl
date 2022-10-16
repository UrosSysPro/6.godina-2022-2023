varying vec2 v_pos;
uniform vec2 screenSize;

void main() {
    vec2 c=v_pos*2.0;
    c.x*=screenSize.x/screenSize.y;
    vec2 z=vec2(0.0);
    for(int i=0;i<50;i++){
        vec2 zn=vec2(0.0);
        zn.x=z.x*z.x-z.y*z.y;
        zn.y=2.0*z.x*z.y;
        zn+=c;
        z=zn;
    }
    vec4 color=vec4(1.0);
    float l=sqrt(z.x*z.x+z.y*z.y);
    if(l<2.0){
        color=vec4(0.0);
    }
    gl_FragColor=color;
}
