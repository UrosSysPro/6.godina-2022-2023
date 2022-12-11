package org.systempro.project.ui;

import com.badlogic.gdx.math.Vector2;

public abstract class Widget {
    Vector2 position;
    Size size;
    public Widget(){
        position=new Vector2(0,0);
        size=new Size(0,0);
    }

    public abstract void draw(WidgetRenderer renderer);
    public abstract Size calculateSize(Size maxSize);
    public abstract Vector2 calculateLocation(Vector2 parentLocation);
    public abstract void animate(float delta);
}
