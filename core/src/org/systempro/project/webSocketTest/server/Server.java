package org.systempro.project.webSocketTest.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.systempro.project.scalaui.Key;
import org.systempro.project.scalaui.widgets.Text;

import java.net.InetSocketAddress;
import java.util.ArrayList;


public class Server extends WebSocketServer {

    public Server(int port){
        super(new InetSocketAddress(port));
    }

    public ArrayList<WebSocket> clients;
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("neko se povezao");
        conn.send("cao");
        clients.add(conn);

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("neko je otisao");
        clients.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Key<Text>[] texts=ServerUI.textKeys();
        for(int i=0;i<texts.length-1;i++){
            texts[i].widget().text_$eq(texts[i+1].widget().text());
        }
        texts[texts.length-1].widget().text_$eq(message);
        for(WebSocket ws :clients){
            ws.send(message);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {
        clients=new ArrayList<>();
        System.out.println("server radi");
    }
}
