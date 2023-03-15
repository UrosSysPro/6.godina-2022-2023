package org.systempro.project.verlet2d;

import java.util.ArrayList;

public class HashTable {
    public ArrayList<Particle>[][] cells;
    public int worldWidth,worldHeight,cellSize;

    public HashTable(int width,int height,int cellSize){
        worldWidth=width;
        worldHeight=height;
        this.cellSize=cellSize;
        cells=new ArrayList[width/cellSize+1][height/cellSize+1];
        for(int i=0;i< cells.length;i++){
            for(int j=0;j<cells[i].length;j++){
                cells[i][j]= new ArrayList<>();
            }
        }
    }
    public void removeAll(){
        for (ArrayList<Particle>[] cell : cells) {
            for (ArrayList<Particle> particles : cell) {
                particles.clear();
            }
        }
    }
    public void insert(Particle p){
        int i=(int)(p.position.x)/cellSize;
        int j=(int)(p.position.y)/cellSize;
        if(i>=0&&j>=0&&i<cells.length&&j<cells[i].length){
            cells[i][j].add(p);
        }
    }
}
