package org.systempro.project.mc;

public class World {
    private Chunk[][] chunks;

    public World(){
        chunks=new Chunk[5][5];
        for(int i=0;i<chunks.length;i++){
            for(int j=0;j<chunks[i].length;j++){
                chunks[i][j]=new Chunk();
            }
        }
    }

    public BlockState getBlockState(int x,int y,int z){
        return chunks[x/16][z/16].blockStates[x%16][y][z%16];
    }
}
