varying vec2 v_pos;
uniform vec2 mouse;

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

//    vec2 p3=p;
//    p3=translate(p3,mouse);
//    float d3=circleSdf(p3,20.0);

    float d=min(d1,d2);
//    d=min(d,d3);

    return d;
}
float rayMarch(vec2 p,vec2 lightPos){
    vec2 ro=p;
    vec2 rd=normalize(lightPos-ro);
    float d=0.0;

    for(int i=0;i<100;i++){
        p=ro+rd*d;
        float currentD=max(0.0,sceneSdf(p));
        float step=min(currentD,length(p-lightPos));
        d+=step;
    }
    return d;
}

void main(){
    float d=sceneSdf(v_pos);
    float lightDistance=rayMarch(v_pos,mouse);

    float alpha=length(mouse-v_pos)-lightDistance;
    alpha=float(alpha<0.1);

    gl_FragColor=mix(vec4(0.0),vec4(1.0),alpha);
}