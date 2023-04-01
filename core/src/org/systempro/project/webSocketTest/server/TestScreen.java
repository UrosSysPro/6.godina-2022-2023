package org.systempro.project.webSocketTest.server;

import org.systempro.project.BasicScreen;


public class TestScreen extends BasicScreen {

    Server server;

    @Override
    public void show() {
        server=new Server(80);
        server.start();
        System.out.println("eeee");

    }

    @Override
    public void render(float delta) {

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
