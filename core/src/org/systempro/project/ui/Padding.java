package org.systempro.project.ui;

import com.badlogic.gdx.math.Vector2;

public class Padding extends SingleChildWidget{
    float padding;
    public Padding(){
        super();
        padding=0;
    }
    public Padding(Widget child){
        super(child);
        padding=0;
    }

    public Padding setPadding(float padding) {
        this.padding = padding;
        return this;
    }

    public float getPadding() {
        return padding;
    }

    @Override
    public Size calculateSize(Size maxSize) {
        Widget child=getChild();
        if(child!=null){
            size.set(maxSize);
            size.width-=2*padding;
            size.height-=2*padding;
            Size childSize=child.calculateSize(size);
            size.set(childSize);
            size.width+=2*padding;
            size.height+=2*padding;
        }else{
            size.set(maxSize);
        }
        return size;
    }

    @Override
    public Vector2 calculateLocation(Vector2 parentLocation) {
        Widget child=getChild();
        if(child!=null){
            position.set(parentLocation);
            position.add(padding,padding);
            child.calculateLocation(position);
            position.set(parentLocation);
        }else{
            position.set(parentLocation);
        }
        return position;
    }
}
