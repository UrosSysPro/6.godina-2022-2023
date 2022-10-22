varying vec2 v_pos;
uniform vec2 screenSize;
uniform vec3 info;

void main() {
    vec2 c=v_pos*2.0;
    c.x*=screenSize.x/screenSize.y;

//    c-=info.xy;
    c*=info.z;
    c+=info.xy;
//    c+=info.xy*info.z;

    vec2 z=vec2(0.0);
    int izlaz=0;
    for(int i=0;i<50;i++){
        vec2 zn=vec2(0.0);
        zn.x=z.x*z.x-z.y*z.y;
        zn.y=2.0*z.x*z.y;
        zn+=c;
        z=zn;
        float l=sqrt(z.x*z.x+z.y*z.y);
        if(l<2.0){
            izlaz=i;
        }
    }
    float alfa=float(izlaz)/50.0;
    vec4 color=vec4(1.0)*alfa+vec4(0.0)*(1.0-alfa);
    gl_FragColor=color;
}
