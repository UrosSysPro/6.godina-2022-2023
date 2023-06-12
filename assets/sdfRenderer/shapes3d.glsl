#version 130

varying vec2 v_position;
uniform vec2 screenSize;
uniform mat4 camera;
uniform float time;

vec3 translate(vec3 position,vec3 translation){
    return position-translation;
}
vec3 rotateX(vec3 position,float angle){
    float s=sin(angle);
    float c=cos(angle);
    return mat3(
            vec3(1.0,0.0,0.0),
            vec3(0.0,  c,  s),
            vec3(0.0, -s,  c)
    )*position;
}
vec3 rotateZ(vec3 position,float angle){
    float s=sin(angle);
    float c=cos(angle);
    return mat3(
        vec3(  c,  s,0.0),
        vec3( -s,  c,0.0),
        vec3(0.0,0.0,1.0)
    )*position;
}
vec3 rotateY(vec3 position,float angle){
    float s=sin(angle);
    float c=cos(angle);
    return mat3(
        vec3(  c,0.0,  s),
        vec3(0.0,1.0,0.0),
        vec3( -s,0.0,  c)
    )*position;
}

float sphere( vec3 p, float s )
{
    return length(p)-s;
}
float plane(vec3 p){
    return p.y;
}

float box( vec3 p, vec3 b )
{
    vec3 q = abs(p) - b;
    return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0);
}
float boxFrame(vec3 position,vec3 size,float width){
    float d=box(position,size);

    float d1=box(position,vec3(1000.0,size.y-width,size.z-width));
    float d2=box(position,vec3(size.x-width,1000.0,size.z-width));
    float d3=box(position,vec3(size.x-width,size.y-width,1000.0));
    return  max(-d1,
            max(-d2,
            max(-d3,d)));
}


vec4 getColor(vec3 position){
    vec4 color=vec4(0.0);
    int i,j,k;

    vec3 p1=translate(position,vec3(0.0,100.0,-200.0));
    float d1=sphere(p1,100.0);
    float pitch=atan(p1.y,100.0);
    float yaw=atan(p1.x,p1.z);
    i=int(pitch*10.0);
    j=int(yaw*10.0);
    vec4 sphereColor=mix(vec4(0.5,0.2,0.7,1.0),vec4(0.6,0.3,0.9,1.0),float(i%2==j%2));
    color=mix(color,sphereColor,float(d1<1.0));
//    if(d1<1.0)color=sphereColor;

    float d2=plane(position);
    i=int(position.x/100.0);
    j=int(position.z/100.0);
    vec4 planeColor=mix(vec4(0.5,0.3,0.0,1.0),vec4(0.6,0.4,0.1,1.0),float(i%2==j%2));
    color=mix(color,planeColor,float(d2<1.0));
//    if(d2<1.0)color=planeColor;

    vec3 p3=translate(position,vec3(300.0));
    float d3=boxFrame(p3,vec3(300.0),50);
    i=int(position.x/25.0);
    j=int(position.y/25.0);
    k=int(position.z/25.0);
    vec4 boxColor=mix(vec4(0.5,0.2,0.7,1.0),vec4(0.6,0.3,0.9,1.0),float(i%2==(j+k%2)%2));
    color=mix(color,boxColor,float(d3<1.0));
//    if(d3<1.0)color=boxColor;

    return color;
}



float sceneSdf(vec3 position){
    float d1=sphere(position-vec3(0.0,100.0,-200.0),100.0);
    float d2=plane(position);
    float d3=boxFrame(
        translate(position,vec3(300.0)),
        vec3(300.0),50);
    return min(min(d1,d2),d3);
}

float rayMarch(vec3 position,vec3 direction,int rayIterations){
    float d=0.0;
    for(int i=0;i<rayIterations;i++){
        float rayDistance=sceneSdf(position+d*direction);
        if(rayDistance<0.0)break;
        d+=rayDistance;
    }
    return d;
}


//float rayMarchShadow(vec3 position,vec3 direction,int rayIterations,float k){
//    float d=0.0;
//    float res=1.0;
//    for(int i=0;i<rayIterations;i++){
//        float rayDistance=sceneSdf(position+d*direction);
//        if(rayDistance<0.0)return 0.0;
//        res=min(res,k*rayDistance/d);
//        d+=rayDistance;
//    }
//    return res;
//}
//
//float softshadow( in vec3 ro, in vec3 rd, float mint, float maxt, float w )
//{
//    float res = 1.0;
//    float t = mint;
//    for( int i=0; i<256 && t<maxt; i++ )
//    {
//        float h = sceneSdf(ro + t*rd);
//        res = min( res, h/(w*t) );
//        t += clamp(h, 0.005, 0.50);
//        if( res<-1.0 || t>maxt ) break;
//    }
//    res = max(res,-1.0);
//    return 0.25*(1.0+res)*(1.0+res)*(2.0-res);
//}


float getShadow(vec3 position,vec3 target,int rayIteraitons){
    float targetDistance=length(target-position);
    vec3 direction=normalize(target-position);
    //hard shadows
    float rayDistance=rayMarch(position+direction*0.1,direction,rayIteraitons);
    return mix(0.0,1.0,float(rayDistance>targetDistance));
    //distance transparent shadows
//    rayDistance=min(rayDistance,targetDistance);
//    return rayDistance/targetDistance;
    //soft shadows
//    return softshadow(position+direction+0.1,direction,0.0,1000.0,10.0);
}

vec3 getNormal(vec3 position){
    return normalize(vec3(
        sceneSdf(position+vec3(0.1,0.0,0.0))-sceneSdf(position-vec3(0.1,0.0,0.0)),
        sceneSdf(position+vec3(0.0,0.1,0.0))-sceneSdf(position-vec3(0.0,0.1,0.0)),
        sceneSdf(position+vec3(0.0,0.0,0.1))-sceneSdf(position-vec3(0.0,0.0,0.1))
    ));
}

vec4 getPhongShading(vec4 color,vec3 normal,float shadow,vec3 lightPosition,vec3 cameraPosition,vec3 worldPosition){
    vec3 lightDirection=normalize(lightPosition-worldPosition);
    vec4 lightColor=vec4(2.0);
    float ambient=0.1;
    float diffuse=max(dot(normal,lightDirection),0.0);
    float specular=max(pow(dot(normalize(cameraPosition-worldPosition),reflect(-lightDirection,normal)),64.0),0.0);
    return color*(ambient+(specular+diffuse)*shadow)*lightColor;
}

void main(){
    float focusLength=1000.0;
    vec3 focusPoint=vec3(0.0,0.0,focusLength);
    vec3 position=vec3(gl_FragCoord.xy-(screenSize*0.5),0.0);
//    vec3 focusPoint=vec3(0.0);
//    vec3 position=vec3(gl_FragCoord.xy-(screenSize*0.5),-focusLength);


    vec4 helper;
    helper=camera*vec4(focusPoint,1.0);
    focusPoint=helper.xyz;
    helper=camera*vec4(position,1.0);
    position=helper.xyz;
    vec3 direction=normalize(position-focusPoint);

    float rayDistance=rayMarch(position,direction,100);
    vec3 finalPosition=position+direction*rayDistance;

//    vec3 lightPosition=vec3(250.0,1000.0,250.0);
    vec3 lightPosition=vec3(1000.0);

    vec4 color=getColor(finalPosition);
    vec3 normal=getNormal(finalPosition);
    float shadow=getShadow(finalPosition,lightPosition,100);

    vec4 cameraPosition=camera*vec4(vec3(0.0),1.0);
    gl_FragColor=getPhongShading(color,normal,shadow,lightPosition,cameraPosition.xyz,finalPosition);
}