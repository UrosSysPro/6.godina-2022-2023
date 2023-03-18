package org.systempro.project.snake3d;

public class SnakePart {
    public int x,y;
    public SnakePart(int x,int y){
        this.x=x;
        this.y=y;
    }
    public SnakePart(){
        this(0,0);
    }
}
