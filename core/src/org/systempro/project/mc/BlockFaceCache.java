package org.systempro.project.mc;

public class BlockFaceCache {
    public int strana,
    localPositionX,localPositionY,localPositionZ;
    public Block block;
    public BlockFaceCache(
        int strana,
        Block block,
        int localPositionX,
        int localPositionY,
        int localPositionZ
    ){
        this.strana=strana;
        this.block=block;
        this.localPositionX=localPositionX;
        this.localPositionY=localPositionY;
        this.localPositionZ=localPositionZ;
    }
}
