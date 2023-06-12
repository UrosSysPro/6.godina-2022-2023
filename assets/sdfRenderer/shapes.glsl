varying vec2 v_position;
uniform vec2 mouse;

float circle( vec2 position, float radius )
{
    return length(position) - radius;
}

float rect( in vec2 position, in vec2 size )
{
    vec2 d = abs(position)-size;
    return length(max(d,0.0)) + min(max(d.x,d.y),0.0);
}

float sceneSdf(vec2 position){
    float d1=circle(position-vec2(300.0),100.0);
    float d2=rect(position-vec2(100.0),vec2(20.0,30.0));
    return min(d1,d2);
}

float reyMarch(vec2 position,vec2 target,int rayIterations){
    vec2 direction=normalize(target-position);
    float d=0.0;
    for(int i=0;i<rayIterations;i++){
        vec2 current=position+direction*d;
        float minDistance=sceneSdf(current);
        float targetDistance=length(current-target);
        d+=min(minDistance,targetDistance-1.0);
    }
    return d;
}

void main(){
    float lightDistance=length(mouse-gl_FragCoord.xy);
    float reyDistance=reyMarch(gl_FragCoord.xy,mouse,20);
    if(abs(reyDistance-lightDistance)>5.0)
//    if(sceneSdf(gl_FragCoord.xy)>0.0)
        gl_FragColor=vec4(0.4,0.5,0.9,1.0);
    else
        gl_FragColor=vec4(0.0);
}