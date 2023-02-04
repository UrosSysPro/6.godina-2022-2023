#version 330

varying vec2 v_pos;
uniform vec2 mouse;
uniform float rayIterations;

vec2 translate(vec2 p,vec2 t){
    return p-t;
}
vec2 rotate(vec2 v, float a) {
    float s = sin(a);
    float c = cos(a);
    mat2 m = mat2(c, -s, s, c);
    return m * v;
}
vec2 duplicate(vec2 p,vec2 b){
    return mod(p,b);
}

float rectSdf( in vec2 p, in vec2 b ){
    vec2 d = abs(p)-b;
    return length(max(d,0.0)) + min(max(d.x,d.y),0.0);
}
float circleSdf(vec2 p,float r){
    return length(p)-r;
}

float sceneSdf(vec2 p){
    vec2 p1=p;
    p1=translate(p1,vec2(400.0,500.0));
    float d1=rectSdf(p1,vec2(200.0,50.0));

    vec2 p2=p;
    p2=translate(p2,vec2(600.0,200.0));
    float d2=rectSdf(p2,vec2(50.0,200.0));

    float d=min(d1,d2);

    return d;
}
float rayMarch(vec2 p,vec2 lightPos){
    vec2 ro=p;
    vec2 rd=normalize(lightPos-ro);
    float d=0.0;

    for(float i=0.0;i<rayIterations;i++){
        p=ro+rd*d;
        float currentD=sceneSdf(p);
        float step=min(currentD,length(p-lightPos)-0.5);
        d+=step;
    }
    return d;
}

void main(){
    vec2 p=v_pos;

    float d=sceneSdf(p);
    float maxRayDistance=rayMarch(p,mouse);
    float ligntDistance=length(mouse-p);

    float delta=abs(ligntDistance-maxRayDistance);
    delta=float(delta<1.0);

    float intensity=1.0-smoothstep(0.0,1000.0,ligntDistance);

    vec4 shadow=mix(vec4(0.0),vec4(1.0),delta)*intensity;
    gl_FragColor=mix(shadow,vec4(1.0),float(d<0.0));
}