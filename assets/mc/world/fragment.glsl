varying vec2 color;
varying vec2 v_texCoord;
varying vec3 v_normal;
varying vec3 v_position;
uniform sampler2D texture0;
uniform vec3 sunDirection;
uniform vec3 cameraPosition;

void main(){
    vec4 color=texture2D(texture0,v_texCoord);
    float ambient=0.3;
    float diffuse=max(0.0,dot(v_normal,-sunDirection));
    float specular=pow(max(dot(normalize(cameraPosition-v_position),reflect(sunDirection,v_normal)),0.0),256.0);
    gl_FragColor=color*(ambient+diffuse*0.7+specular);
//    gl_FragColor=vec4(normalize(cameraPosition-v_position),1.0);
}