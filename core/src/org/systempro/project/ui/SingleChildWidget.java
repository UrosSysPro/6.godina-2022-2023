package org.systempro.project.ui;

import com.badlogic.gdx.math.Vector2;

public class SingleChildWidget extends Widget{
    private Widget child;

    public SingleChildWidget(Widget child){
        super();
        this.child=child;
    }
    public SingleChildWidget(){
        super();
        child=null;
    }

    public SingleChildWidget setChild(Widget child) {
        this.child = child;
        return this;
    }

    public Widget getChild() {
        return child;
    }

    @Override
    public void draw(WidgetRenderer renderer) {
        if(child!=null)child.draw(renderer);
    }

    @Override
    public Size calculateSize(Size maxSize) {
        if(child!=null){
            Size childSize=child.calculateSize(maxSize);
            size.set(childSize);
        }else{
            size.set(maxSize);
        }
//        System.out.println(this.getClass().getName()+" "+size.width+" "+size.height);
        return size;
    }

    @Override
    public Vector2 calculateLocation(Vector2 parentLocation) {
        position.set(parentLocation);
        if(child!=null){
            child.calculateLocation(position);
        }
        return position;
    }

    @Override
    public void animate(float delta) {
        if(child!=null)child.animate(delta);
    }
}
