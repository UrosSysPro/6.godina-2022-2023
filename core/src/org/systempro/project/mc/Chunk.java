package org.systempro.project.mc;

public class Chunk {
    public BlockState[][][] blockStates;

    public Chunk(int x,int y,PerlinNoise2d noise2d){
        blockStates=new BlockState[16][256][16];
        for(int i=0;i<16;i++){
            for(int k=0;k<16;k++){
                float dx=x+i,dy=y+k;
                dx/=10;
                dy/=10;
                float height=noise2d.get(dx,dy);
                height=height/2+0.5f;
                height=height*10+10;
                for(int j=0;j<256;j++){
                    Block b=Block.AIR;
                    if(j<height)b=Block.STONE;
                    blockStates[i][j][k]=new BlockState(b);
                }
            }
        }
    }
}
