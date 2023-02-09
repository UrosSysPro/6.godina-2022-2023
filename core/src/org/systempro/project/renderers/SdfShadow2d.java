package org.systempro.project.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.camera.Camera2d;

public class SdfShadow2d implements Disposable {

    private final Mesh mesh;
    private final ShaderProgram shader;
    public Camera2d camera2d;
    public Texture texture;
    public TextureRegion region;
    public FrameBuffer buffer;
    public float bufferWidth,bufferHeight;
    public float renderWidth, renderHeight;
    public float worldWidth,worldHeight;


    public SdfShadow2d(float worldWidth,float worldHeight,float renderWidth,float renderHeight){

        mesh=new Mesh(true,4,6,
            new VertexAttribute(VertexAttributes.Usage.Position,2,"pos")
        );
        bufferWidth=1920;
        bufferHeight=1080;

        mesh.setIndices(new short[]{
            0,1,2,
            1,3,2
        });

        ShaderProgram.pedantic=false;
        String vertex=Gdx.files.internal("sdfShadow2d/vertex.glsl").readString();
        String fragment=Gdx.files.internal("sdfShadow2d/fragment.glsl").readString();
        shader=new ShaderProgram(vertex,fragment);

        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }

        camera2d=new Camera2d();
        camera2d.setSize(bufferWidth,bufferHeight);
        camera2d.setPosition(bufferWidth/2,bufferHeight/2);
        camera2d.update();

        GLFrameBuffer.FrameBufferBuilder builder=new GLFrameBuffer.FrameBufferBuilder((int)bufferWidth,(int)bufferHeight);
        builder.addBasicColorTextureAttachment(Pixmap.Format.RGBA8888);
        buffer=builder.build();
        texture=buffer.getColorBufferTexture();
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        region=new TextureRegion(texture);

        resize(worldWidth,worldHeight,renderWidth,renderHeight);
    }

    public void render(int rayIterations){
        buffer.begin();
        ScreenUtils.clear(0,0,0,1);
        shader.bind();
        shader.setUniformMatrix("camera",camera2d.combined4);
        shader.setUniformf("mouse",new Vector2(Gdx.input.getX(),worldHeight-Gdx.input.getY()));
        shader.setUniformf("rayIterations",rayIterations);
        shader.setUniformf("worldSize",new Vector2(worldWidth,worldHeight));
        shader.setUniformf("renderSize",new Vector2(renderWidth, renderHeight));
        mesh.render(shader, GL20.GL_TRIANGLES);
        buffer.end();
    }
    public void resize(float worldWidth,float worldHeight,float renderWidth,float renderHeight){
        this.worldWidth=worldWidth;
        this.worldHeight=worldHeight;
        this.renderWidth=renderWidth;
        this.renderHeight =renderHeight;
        region.setRegion(0,0,(int)renderWidth,(int)renderHeight);
        mesh.setVertices(new float[]{
            0,     0,
            0,renderHeight,
            renderWidth,     0,
            renderWidth,renderHeight
        });
    }

    @Override
    public void dispose() {
        mesh.dispose();
        shader.dispose();
        buffer.dispose();
    }
}
