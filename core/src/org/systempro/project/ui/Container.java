package org.systempro.project.ui;

import com.badlogic.gdx.graphics.Color;

public class Container extends SingleChildWidget{
    private Color color;

    public Container(Widget child){
        super(child);
    }
    public Container(){
        super();
    }

    @Override
    public void draw(WidgetRenderer renderer) {
        renderer.draw(position.x,position.y,size.width,size.height,Color.RED);
        super.draw(renderer);
    }
}
