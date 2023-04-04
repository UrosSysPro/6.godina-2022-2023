package org.systempro.project.webSocketTest.server;

import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;


public class TestScreen extends BasicScreen {

    Server server;

    @Override
    public void show() {
        server=new Server(3000);
        server.start();
        ServerUI.load();
        System.out.println("eeee");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        ServerUI.scene().layout();
        ServerUI.scene().draw();
    }

    @Override
    public void dispose() {
        try {
            server.stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void hide() {
        try {
            server.stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
