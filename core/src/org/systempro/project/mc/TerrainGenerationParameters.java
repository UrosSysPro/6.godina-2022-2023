package org.systempro.project.mc;

public class TerrainGenerationParameters {
    public float density;
    public int baseHeight,randomHeight;
    public TerrainGenerationParameters(float density,int baseHeight,int randomHeight){
        this.baseHeight=baseHeight;
        this.density=density;
        this.randomHeight=randomHeight;
    }
}
