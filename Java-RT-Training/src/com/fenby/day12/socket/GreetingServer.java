/**
 * --------------------------------------------------------------------
 * Copyright (c) 2014 by VIPHold Technology.
 * This software is the proprietary information of VIPHold Technology
 * All Right Reserved.
 * --------------------------------------------------------------------
 */
package com.fenby.day12.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GreetingServer  {
    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(60000);
    }

    public void process() {

        while (true) {
            try {
                System.out.println("服务器监听端口: " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("客户端Socket地址: " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("感谢访问<" + server.getLocalSocketAddress() + ">, 再见!");
                server.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket超时");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        int port = 6666;
        try {
            GreetingServer greetingServer = new GreetingServer(port);
            greetingServer.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}