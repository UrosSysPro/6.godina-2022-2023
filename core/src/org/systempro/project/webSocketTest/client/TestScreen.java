package org.systempro.project.webSocketTest.client;

import org.systempro.project.BasicScreen;

import java.net.URI;
import java.net.URISyntaxException;

public class TestScreen extends BasicScreen {

    Client client;

    @Override
    public void show() {
        try {
            client=new Client(new URI("ws://localhost:3000"));
            client.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void render(float delta) {

    }
}
