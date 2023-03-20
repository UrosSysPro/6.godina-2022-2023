package org.systempro.project.basics3d;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
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


    public static void enableInstancing(Mesh mesh,int maxInstances){
        mesh.enableInstancedRendering(true,maxInstances,
            new VertexAttribute(VertexAttributes.Usage.Generic,4,"col0"),
            new VertexAttribute(VertexAttributes.Usage.Generic,4,"col1"),
            new VertexAttribute(VertexAttributes.Usage.Generic,4,"col2"),
            new VertexAttribute(VertexAttributes.Usage.Generic,4,"col3")
        );
    }
}
