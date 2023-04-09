package org.systempro.project.verlet2d;

import java.util.concurrent.Future;

public class CollisionRunnable implements Runnable{
    Future<Void> dependency1,dependency2;
    int start,end;
    HashTable table;
    public CollisionRunnable(HashTable table, int start, int end, Future<Void> dependency1,Future<Void> dependency2){
        this.dependency1=dependency1;
        this.dependency2=dependency2;
        this.start=start;
        this.end=end;
        this.table=table;
    }

    private void collideWithNearCells(HashTable table,Particle p1,int x,int y){
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if(x+i<0||x+i>=table.cells.length||y+j<0||y+j>=table.cells[0].length)continue;
                for(Particle p2:table.cells[x+i][y+j]){
                    if(p1==p2)continue;
                    Particle.resolveCollision(p1,p2);
                }
            }
        }
    }


    @Override
    public void run() {
        try{
            if(dependency1!=null)dependency1.get();
            if(dependency2!=null)dependency2.get();
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=start;i<=end;i++){
            for(int j=0;j<table.cells[i].length;j++){
                for(Particle p1:table.cells[i][j]){
                    collideWithNearCells(table,p1,i,j);
                }
            }
        }
    }
}
