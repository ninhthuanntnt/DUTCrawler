package com.ntnt.dut.crawlerapi;

import com.ntnt.dut.crawlerapi.websockets.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class CrawlerApiApplication {

    public static void main(String[] args) {

//        try {
//            WebSocketServer webSocketServer = new WebSocketServer();
//            webSocketServer.run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        SpringApplication.run(CrawlerApiApplication.class, args);
    }

}
