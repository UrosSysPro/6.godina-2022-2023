package org.systempro.project.mc;

public class World {
    private Chunk[][] chunks;
    public BlockState airState=new BlockState(Block.AIR);

    public World(){
        PerlinNoise2d noise2d=new PerlinNoise2d();
        TerrainGenerationParameters params=new TerrainGenerationParameters(
          20,
          3,
          30
        );
        chunks=new Chunk[20][20];
        for(int i=0;i<chunks.length;i++){
            for(int j=0;j<chunks[i].length;j++){
                chunks[i][j]=new Chunk(i,j,noise2d,params);
            }
        }
    }

    public BlockState getBlockState(int x,int y,int z){
        if(x<0||x>=chunks.length*16 ||
            y<0||y>=256||
            z<0||z>=chunks[0].length*16
        )return airState;
        return chunks[x/16][z/16].blockStates[x%16][y][z%16];
    }

    public void render(BlockFaceRenderer renderer){
        for(int i=0;i< chunks.length;i++){
            for(int j=0;j<chunks[i].length;j++){
                for(BlockFaceCache cache:chunks[i][j].cache){
                    renderer.draw(cache,i,j);
                }
            }
        }
        renderer.flush();
    }
    public void generateCache(){
        for(int i=0;i< chunks.length;i++){
            for(int j=0;j<chunks[i].length;j++){
                chunks[i][j].generateCache(this,i,j);
            }
        }
    }

    public int  calculateSize(){
        //2 reference velicine 4 bajta
        int size=chunks.length*chunks[0].length*chunks[0][0].calculateSize()*2*4;
        return  size;
    }
    public int calculateCacheSize(){
        int size=0;

        for(int i=0;i<chunks.length;i++){
            for(int j=0;j<chunks[0].length;j++){
                size+=chunks[i][j].cache.size()*(4+5*4);
            }
        }

        return size;
    }
}
