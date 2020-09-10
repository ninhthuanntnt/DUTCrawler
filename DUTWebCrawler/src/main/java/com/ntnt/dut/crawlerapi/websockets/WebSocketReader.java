package com.ntnt.dut.crawlerapi.websockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WebSocketReader implements Runnable{

    private Socket socket;

    public WebSocketReader(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Start listening......");
            while(true){
                String line = bf.readLine();
                if(line != null){
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
