package org.systempro.project.ui;

import com.badlogic.gdx.math.Vector2;

public class SingleChildWidget extends Widget{
    private Widget child;

    public SingleChildWidget(Widget child){
        this.child=child;
    }

    public SingleChildWidget setChild(Widget child) {
        this.child = child;
        return this;
    }

    @Override
    public void draw() {

    }

    @Override
    public Size calculateSize(Size maxSize) {
        return null;
    }

    @Override
    public Vector2 calculateLocation(Vector2 parentLocation) {
        return null;
    }

    @Override
    public void animate(float delta) {

    }
}
