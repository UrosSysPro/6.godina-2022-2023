package org.systempro.project.mc;

import java.util.ArrayList;

public class Chunk {

    public ArrayList<BlockFaceCache> cache;
    public BlockState[][][] blockStates;

    public Chunk(int x,int y,PerlinNoise2d noise2d,TerrainGenerationParameters params){
        cache=new ArrayList<>();
//        blockStates=new BlockState[16][256][16];

        blockStates=new BlockState[16][][];
        for(int i=0;i<16;i++){
            blockStates[i]=new BlockState[256][];
            for(int j=0;j<256;j++){
                blockStates[i][j]=new BlockState[16];
            }
        }

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

    public void generateCache(World world,int chunkX,int chunkY){
        cache.clear();
        for(int i=0;i<16;i++){
            for(int j=0;j<256;j++){
                for(int k=0;k<16;k++){
                    BlockState state=blockStates[i][j][k];
                    if(state.getBlock()==Block.AIR)continue;
                    int x=chunkX*16+i;
                    int y=j;
                    int z=chunkY*16+k;
                    if(world.getBlockState(x,y,z+1).getBlock()==Block.AIR){
                        cache.add(new BlockFaceCache(0,state.getBlock(), i, j,k));
                    }
                    if(world.getBlockState(x,y,z-1).getBlock()==Block.AIR){
                        cache.add(new BlockFaceCache(1,state.getBlock(), i, j, k));
                    }
                    if(world.getBlockState(x+1,y,z).getBlock()==Block.AIR){
                        cache.add(new BlockFaceCache(2,state.getBlock(), i, j,k));
                    }
                    if(world.getBlockState(x-1,y,z).getBlock()==Block.AIR){
                        cache.add(new BlockFaceCache(3,state.getBlock(),i, j, k));
                    }
                    if(world.getBlockState(x,y+1,z).getBlock()==Block.AIR){
                        cache.add(new BlockFaceCache(4,state.getBlock(),i,j, k));
                    }
                    if(world.getBlockState(x,y-1,z).getBlock()==Block.AIR){
                        cache.add(new BlockFaceCache(5,state.getBlock(),i, j,k));
                    }
                }
            }
        }
    }

    public int  calculateSize(){
        return blockStates.length*blockStates[0].length*blockStates[0][0].length;
    }
}
