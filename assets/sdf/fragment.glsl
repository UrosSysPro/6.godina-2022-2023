#define PI 3.1415926538
varying vec2 v_pos;
uniform vec2 mouse;
uniform vec2 screenSize;
uniform float time;

//float rand(vec2 co){
//    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
//}

float PHI = 1.61803398874989484820459;  // Φ = Golden Ratio

float rand(in vec2 xy){
    return fract(tan(distance(xy*PHI, xy)*78.233)*xy.x);
}

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
    float tileSize=50.0;

    vec2 tileId=floor(p/tileSize);

    float randomX=float(rand(tileId.xy)>0.5);
    float randomY=float(rand(tileId.yx)>0.5);

    vec2 p1=duplicate(p,vec2(tileSize));
    p1=translate(p1,vec2(tileSize*randomX,tileSize*randomY));
    p1=rotate(p1,PI/4.0);
    float d1=circleSdf(p1,tileSize);

    float d=d1;
    return d;
}


void main(){
    vec4 colors[4];
    colors[0]=vec4(0.95, 0.84, 0.81, 1.0);
    colors[1]=vec4(0.93, 0.59, 0.18, 1.0);
    colors[2]=vec4(0.02, 0.11, 0.25, 1.0);
    colors[3]=vec4(0.05, 0.65, 0.59, 1.0);


    float tileSize=50.0;

    vec2 tileId=floor(v_pos/tileSize);

    float randomX=floor(rand(tileId.xy)*4.0);
    float randomY=floor(rand(tileId.yx)*4.0);

    randomX=mod(randomX+float(randomX==randomY)*2.0,4.0);
    vec4 inColor=colors[int(randomY)];
    vec4 outColor=colors[int(randomX)];

    float d=sceneSdf(v_pos);

    float lineWidth=10.0;
    float gradient=mod(d,lineWidth);
    gradient=mix(0.7,1.0,float(gradient>lineWidth/2.0));

    vec4 color=mix(inColor,outColor,float(d>0.0))*gradient;

    gl_FragColor=color;
}