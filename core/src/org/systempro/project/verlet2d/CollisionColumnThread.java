package org.systempro.project.verlet2d;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class CollisionColumnThread implements Runnable {

    private final ArrayList<Particle>[][] cells;
    private final int i;

//    private Vector2 diff;

    public CollisionColumnThread(ArrayList<Particle>[][] cells,int i){
        this.cells=cells;
        this.i=i;
//        diff=new Vector2();
    }

    @Override
    public void run() {
        for (int j = 0; j < cells[i].length; j++) {
            for (Particle p : cells[i][j]) {
                collideWithNearCells(p, i, j);
            }
        }
    }


    private void collideWithNearCells(Particle p1,int x,int y){
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if(x+i<0||x+i>=cells.length||y+j<0||y+j>=cells[0].length)continue;
                for(Particle p2:cells[x+i][y+j]){
                    if(p1==p2)continue;
                    Particle.resolveCollision(p1,p2);
                }
            }
        }
    }
//    public void resolveCollision(Particle p1,Particle p2){
//        diff.set(
//            p1.position.x-p2.position.x,
//            p1.position.y-p2.position.y
//        );
//        float distance=diff.len();
//        float minDistance=p1.radius+p2.radius;
//        if(distance<minDistance){
//            float delta=minDistance-distance;
////            Vector2 v1=new Vector2(
////                p1.position.x-p1.prevPosition.x,
////                p1.position.y-p1.prevPosition.y
////            );
////            Vector2 v2=new Vector2(
////                p2.position.x-p2.prevPosition.x,
////                p2.position.y-p2.prevPosition.y
////            );
//            p1.position.add(
//                diff.x/distance*delta/2,
//                diff.y/distance*delta/2
//            );
//            p2.position.sub(
//                diff.x/distance*delta/2,
//                diff.y/distance*delta/2
//            );
//        }
//    }

}
