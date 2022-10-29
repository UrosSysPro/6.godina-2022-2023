varying vec2 v_pos;
uniform vec2 mouse;
uniform vec2 screenSize;

void main(){
//    vec2 c=vec2(0.0);
    vec2 c=mouse;
    vec2 z=v_pos*2.0;
    z.x*=screenSize.x/screenSize.y;
    int izlaz=0;
    int preciznost=30;
    for(int i=0;i<preciznost;i++){
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
    float alfa=float(izlaz)/float(preciznost);
    vec4 color=vec4(1.0)*alfa+vec4(0.0)*(1.0-alfa);
    gl_FragColor=color;
}