package org.systempro.project.mc;

public class BlockFaceCache {
    public byte strana,texCoordX,texCoordY,
    localPositionX,localPositionY,localPositionZ;
    public BlockFaceCache(
        byte strana,
        byte texCoordX,
        byte texCoordY,
        byte localPositionX,
        byte localPositionY,
        byte localPositionZ
    ){
        this.strana=strana;
        this.localPositionX=localPositionX;
        this.localPositionY=localPositionY;
        this.localPositionZ=localPositionZ;
        this.texCoordX=texCoordX;
        this.texCoordY=texCoordY;
    }
}
