package org.systempro.project.mc.world;


public class BlockState {
    private Block block;

    public BlockState(Block block){
        this.block=block;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
