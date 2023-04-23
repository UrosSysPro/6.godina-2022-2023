package org.systempro.project.mc;

public class Chunk {
    public BlockState[][][] blockStates;

    public Chunk(int x,int y,PerlinNoise2d noise2d,TerrainGenerationParameters params){
        blockStates=new BlockState[16][256][16];
        for(int i=0;i<16;i++){
            for(int k=0;k<16;k++){
                float dx=x*16+i,dy=y*16+k;
                dx/=params.density;
                dy/=params.density;
                float height=noise2d.get(dx,dy);
                height=height* params.randomHeight+ params.baseHeight;
                for(int j=0;j<256;j++){
                    Block b=Block.AIR;
                    if(j<height)b=Block.STONE;
                    blockStates[i][j][k]=new BlockState(b);
                }
            }
        }
    }
}
