varying vec2 v_texCoord0;
varying vec3 v_normal;
varying vec3 v_position;
varying vec4 v_tangent;

uniform sampler2D texture0;
uniform sampler2D normalMap;
uniform vec3 cameraPosition;


struct Light {
    int type;
    vec3 position;
    vec3 attenuation;
    vec3 direction;
    vec3 color;
};

uniform vec3 ambientColor;
uniform Light lights[10];
uniform int lightCount;


float fallOff(float d,vec3 attenuation){
    return 1.0/(d*d*attenuation.x+d*attenuation.y+attenuation.z);
}

void getLighting(in Light light,out vec3 diffuse,out vec3 specular,vec3 normal,vec3 fragmentPosition){

    float fall=fallOff(length(light.position-fragmentPosition),light.attenuation);

    //diffuse
    vec3 lightDir=normalize(light.position-fragmentPosition);
    diffuse=max(dot(lightDir,normal),0.0)*light.color*fall;

    //specular
    vec3 reflected=reflect(-lightDir,normal);
    vec3 cameraDir=normalize(cameraPosition-fragmentPosition);
    specular=pow(max(dot(reflected,cameraDir),0.0),128.0)*light.color*fall;
}

vec4 totalLighting(){
    vec3 N=normalize(v_normal);
    vec3 T=normalize(v_tangent.xyz);
    vec3 B=cross(N,T);
    mat3 TBN=mat3(T,B,N);
    vec3 normal=texture2D(normalMap,v_texCoord0).xyz*2.0-1.0;
    normal=TBN*normal;

    vec3 ambient=ambientColor;
    vec3 diffuse=vec3(0.0);
    vec3 specular=vec3(0.0);
//    vec3 normal=normalize(v_normal);
    vec3 fragmentPosition=v_position;
    for(int i=0;i<lightCount;i++){
        vec3 d,s;
        getLighting(lights[i],d,s,normal,fragmentPosition);
        diffuse+=d;
        specular+=s;
    }
    return vec4(ambient,1.0)+vec4(specular,1.0)+vec4(diffuse,1.0);
}


void main(){
    vec2 texCoord=v_texCoord0;
    texCoord.y=1.0-texCoord.y;
    vec4 baseColor=texture2D(texture0,texCoord);

    gl_FragColor=totalLighting()*baseColor;
//    gl_FragColor=abs(vec4(B,1.0));
//    gl_FragColor=baseColor;
}