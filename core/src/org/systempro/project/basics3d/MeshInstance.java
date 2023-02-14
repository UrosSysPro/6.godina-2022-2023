package org.systempro.project.basics3d;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class MeshInstance {
    public Vector3 position;
    public Vector3 scale;
    public Quaternion rotation;
    public Matrix4 transform;

    public MeshInstance(){
        position=new Vector3(0,0,0);
        scale=new Vector3(1,1,1);
        rotation=new Quaternion();
        transform=new Matrix4();
    }

    public void update(){
        transform.idt()
            .translate(position)
            .rotate(rotation)
            .scale(scale.x,scale.y,scale.z);
    }

}
