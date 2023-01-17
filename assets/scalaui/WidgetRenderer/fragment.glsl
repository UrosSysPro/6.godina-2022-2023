varying vec2 v_texCoords;
varying vec4 v_color;
varying vec4 v_borderColor;
varying float v_borderRadius;
varying float v_borderWidth;
varying float v_blur;
varying float v_imageAlpha;
varying vec2 v_size;


uniform sampler2D font;

float rectSdf(vec2 pos,vec2 size){
    vec2 d = abs(pos)-size;
    return length(max(d,0.0)) + min(max(d.x,d.y),0.0);
}

void main(){
    float blur=max(v_blur,0.0),d,alfa;
    vec2 pos=(v_texCoords-0.5)*v_size;
    //obrder color
    d=rectSdf(pos,v_size/2.0-v_borderRadius-blur);
    d-=v_borderRadius;
    alfa=1.0-smoothstep(0.0,blur,d);
    vec4 outterColor=v_borderColor*alfa;
    //inner color
    float innerBorderRadius=v_borderRadius-v_borderWidth;
    d=rectSdf(pos,v_size/2.0-innerBorderRadius-blur-v_borderWidth);
    d-=innerBorderRadius;
    alfa=1.0-smoothstep(0.0,blur,d);
//    alfa=1.0-d/blur;
    vec4 innerColor=v_color*alfa;

    vec4 sdfColor=mix(outterColor,innerColor,alfa);
    gl_FragColor=mix(sdfColor,texture2D(font,v_texCoords)*v_color,v_imageAlpha);

}