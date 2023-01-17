package org.systempro.project.scalaui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {
    public static BitmapFont roboto;

    public static void load(){
        FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontParameter parameter=new FreeTypeFontParameter();
        parameter.color= Color.WHITE;
        parameter.flip=true;
        parameter.size=40;
        roboto=generator.generateFont(parameter);
        generator.dispose();
    }
    public static void dispose(){
        roboto.dispose();
    }
}
