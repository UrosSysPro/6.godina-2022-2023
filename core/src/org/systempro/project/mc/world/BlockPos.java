package org.systempro.project.mc.world;

import java.util.Objects;

public class BlockPos {
    int x,y,z;

    public BlockPos(int x,int y,int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public BlockPos(){
        this(0,0,0);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)return false;
        if(obj==this)return  true;
        if(!(obj instanceof BlockPos))return false;
        BlockPos pos= (BlockPos) obj;
        return x==pos.x && y==pos.y&& z==pos.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y,z);
    }
}
