varying vec2 v_texCoord0;
varying vec3 v_normal;
varying vec3 v_position;

uniform sampler2D texture0;
uniform vec3 cameraPosition;


vec4 pointLightColor=vec4(1.0);
vec3 pointLightPosition=vec3(10.0);

void main(){
    vec2 texCoord=v_texCoord0;
    texCoord.y=1.0-texCoord.y;
    vec4 baseColor=texture2D(texture0,texCoord);
    vec3 normal=normalize(v_normal);

    //ambient
    vec4 ambient=vec4(0.2,0.3,0.2,1.0);

    //diffuse
    float diffuseStrenth=0.7;
    vec3 lightDir=normalize(pointLightPosition-v_position);
    vec4 diffuse=max(dot(lightDir,normal),0.0)*pointLightColor*diffuseStrenth;

    //specular
    float specularStrenght=0.3;
    vec3 reflected=reflect(-lightDir,normal);
    vec3 cameraDir=normalize(cameraPosition-v_position);
    vec4 specular=pow(max(dot(reflected,cameraDir),0.0),128.0)*pointLightColor*specularStrenght;

    gl_FragColor=(ambient+diffuse+specular)*baseColor;
//    gl_FragColor=vec4(v_normal,1.0);
}