package org.systempro.project.shadowMapTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.systempro.project.BasicScreen;
import org.systempro.project.basics3d.CameraController;
import org.systempro.project.basics3d.MeshInstance;
import org.systempro.project.basics3d.ShadowMapRenderer;
import org.systempro.project.renderers.TextureRenderer;

public class TestScreen extends BasicScreen {

    Mesh mesh;
    MeshInstance[] instances;
    ShadowMapRenderer shadowMapRenderer;
    TextureRenderer textureRenderer;
    TextureRegion region;
    @Override
    public void show() {

        //mesh
        Model model=new ObjLoader().loadModel(Gdx.files.internal("test3d/kocka.obj"));
        mesh=model.meshes.first();
        mesh.enableInstancedRendering(true,1000,
            new VertexAttribute(VertexAttributes.Usage.Generic,4,"col0"),
            new VertexAttribute(VertexAttributes.Usage.Generic,4,"col1"),
            new VertexAttribute(VertexAttributes.Usage.Generic,4,"col2"),
            new VertexAttribute(VertexAttributes.Usage.Generic,4,"col3")
        );

        shadowMapRenderer=new ShadowMapRenderer(mesh);

        instances=new MeshInstance[26];
        for(int index=0;index<25;index++){
            int i=index%5;
            int j=index/5;
            MeshInstance instance=new MeshInstance();
            instance.position.set(i,0,j);
//            instance.scale.set(1,0.1f,1);
            instances[index]=instance;
        }
        MeshInstance instance=new MeshInstance();
        instance.position.set(2.5f,3,2.5f);
        instances[25]=instance;

        region=new TextureRegion(shadowMapRenderer.buffer.getColorBufferTexture());
        textureRenderer=new TextureRenderer(shadowMapRenderer.buffer.getColorBufferTexture());
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        for(MeshInstance instance:instances){
            shadowMapRenderer.draw(instance);
        }
        shadowMapRenderer.flush();

        Gdx.gl20.glDisable(GL20.GL_CULL_FACE);
        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);

        textureRenderer.draw(region,0,0,600,600);
        textureRenderer.flush();
    }
}
