package org.systempro.project.mc;

public class Chunk {
    public BlockState[][][] blockStates;

    public Chunk(){
        blockStates=new BlockState[16][256][16];
        for(int i=0;i<16;i++){
            for(int j=0;j<256;j++){
                for(int k=0;k<16;k++){
                    Block b=Block.AIR;
                    if(Math.random()>0.5)b=Block.STONE;
                    blockStates[i][j][k]=new BlockState(b);
                }
            }
        }
    }
}
