package org.systempro.project.mc.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chunk {
    public HashMap<BlockPos,BlockState> blockStates;
    public HashMap<BlockPos,BlockFaceCache[]> cache;

    public Chunk(){
        blockStates=new HashMap<>();
        cache=new HashMap<>();
        for(int i=0;i<16;i++){
            for(int j=0;j<60;j++){
                for(int k=0;k<16;k++){
                    BlockPos pos=new BlockPos(i,j,k);
                    Block block=Block.STONE;
                    if(j==58)block=Block.DIRT;
                    if(j==59)block=Block.GRASS;
                    BlockState state=new BlockState(block);
                    blockStates.put(pos,state);
                }
            }
        }
    }
    public void generateCache(World world,int chunkX,int chunkY){
        cache.clear();
        for(Map.Entry<BlockPos,BlockState> entry : blockStates.entrySet()){
            BlockPos pos=entry.getKey();
            BlockState state=entry.getValue();
            ArrayList<BlockFaceCache> list=new ArrayList<>();
            int x=chunkX*16+pos.x;
            int y=pos.y;
            int z=chunkY*16+pos.z;
            if(world.getBlockState(x,y,z+1).getBlock()== Block.AIR){
                list.add(new BlockFaceCache(0,state.getBlock(), pos.x,pos.y,pos.z));
            }
            if(world.getBlockState(x,y,z-1).getBlock()== Block.AIR){
                list.add(new BlockFaceCache(1,state.getBlock(), pos.x,pos.y,pos.z));
            }
            if(world.getBlockState(x+1,y,z).getBlock()== Block.AIR){
                list.add(new BlockFaceCache(2,state.getBlock(), pos.x,pos.y,pos.z));
            }
            if(world.getBlockState(x-1,y,z).getBlock()== Block.AIR){
                list.add(new BlockFaceCache(3,state.getBlock(),pos.x,pos.y,pos.z));
            }
            if(world.getBlockState(x,y+1,z).getBlock()== Block.AIR){
                list.add(new BlockFaceCache(4,state.getBlock(),pos.x,pos.y,pos.z));
            }
            if(world.getBlockState(x,y-1,z).getBlock()== Block.AIR){
                list.add(new BlockFaceCache(5,state.getBlock(),pos.x,pos.y,pos.z));
            }
            if(list.isEmpty())continue;
            BlockFaceCache[] array=new BlockFaceCache[list.size()];
            for(int i=0;i<array.length;i++)array[i]=list.get(i);
            cache.put(pos, array);
        }
    }
}
