package org.systempro.project.mc;

public class World {
    private Chunk[][] chunks;

    public World(){
        PerlinNoise2d noise2d=new PerlinNoise2d();
        TerrainGenerationParameters params=new TerrainGenerationParameters(
          20,
          3,
          30
        );
        chunks=new Chunk[5][5];
        for(int i=0;i<chunks.length;i++){
            for(int j=0;j<chunks[i].length;j++){
                chunks[i][j]=new Chunk(i,j,noise2d,params);
            }
        }
    }

    public BlockState getBlockState(int x,int y,int z){
        return chunks[x/16][z/16].blockStates[x%16][y][z%16];
    }
}
