package org.systempro.project.webSocketTest.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;


public class Server extends WebSocketServer {

    public Server(int port){
        super(new InetSocketAddress(port));
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("neko se povezao");
        conn.send("cao");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("neko je otisao");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("neko kaze "+message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {
        System.out.println("server radi");
    }
}
