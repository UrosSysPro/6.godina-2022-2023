package org.systempro.project.mc.world;

import java.util.Map;

public class World {
    private Chunk[][] chunks;

    public World(){
        chunks=new Chunk[10][10];
        for(int i=0;i<chunks.length;i++){
            for(int j=0;j<chunks[i].length;j++){
                chunks[i][j]=new Chunk();
            }
        }
    }

    public BlockState getBlockState(BlockPos pos){
        if(pos.x/16>=chunks.length||pos.x/16<0||pos.z/16>=chunks[0].length||pos.z/16<0)return new BlockState(Block.AIR);
        int cx=pos.x/16;
        int cy=pos.z/16;
        pos.x=pos.x%16;
        pos.z=pos.z%16;
        BlockState state=chunks[cx][cy].blockStates.get(pos);
        return  state!=null?state:new BlockState(Block.AIR);
    }
    public BlockState getBlockState(int x,int y,int z){
        if(
            x/16>=chunks.length||
                x/16<0||
                z/16>=chunks[0].length||
                z/16<0
        )
            return new BlockState(Block.AIR);
        BlockState state=chunks[x/16][z/16].blockStates.get(new BlockPos(x%16,y,z%16));
        return state!=null?state:new BlockState(Block.AIR);
    }
    public void setBlockState(int x,int y,int z,BlockState state){
        if(x/16>=chunks.length|| x/16<0|| z/16>=chunks[0].length|| z/16<0)return;
        chunks[x/16][z/16].blockStates.put(new BlockPos(x%16,y,z%16),state);
    }
    public void generateCache(){
        for(int i=0;i<chunks.length;i++){
            for(int j=0;j<chunks[i].length;j++){
                chunks[i][j].generateCache(this,i,j);
            }
        }
    }
    public void render(BlockFaceRenderer renderer){
        for(int i=0;i<chunks.length;i++){
            for(int j=0;j<chunks[i].length;j++){
                for(Map.Entry<BlockPos,BlockFaceCache[]> entry:chunks[i][j].cache.entrySet()){
                    BlockFaceCache[] faces=entry.getValue();
                    for (BlockFaceCache face : faces) {
                        renderer.draw(face, i, j);
                    }
                }
            }
        }
        renderer.flush();
    }
}
