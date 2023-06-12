package org.systempro.project.mc.world;

public class Block {
    public int textureX,textureY;
    public Block(int textureX,int textureY){
        this.textureX=textureX;
        this.textureY=textureY;
    }

    public static Block STONE=new Block(2,0);
    public static Block AIR=new Block(0,0);
    public static Block GRASS=new Block(0,0);
    public static Block DIRT=new Block(0,1);
}
