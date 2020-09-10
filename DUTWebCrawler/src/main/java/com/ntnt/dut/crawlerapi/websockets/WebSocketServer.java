package com.ntnt.dut.crawlerapi.websockets;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class WebSocketServer implements Runnable {
    private ServerSocket server;
    private static int port = 4000;
    public static final int MASK_SIZE = 4;
    public static final int SINGLE_FRAME_UNMASKED = 0x81;
    public ArrayList<Socket> sockets;
//    BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
    public WebSocketServer(int customPort) throws IOException {
        server = new ServerSocket(customPort);
        sockets = new ArrayList<>();
    }

    public WebSocketServer() throws IOException {
        server = new ServerSocket(port);
        sockets = new ArrayList<>();
    }

    public void listenerThread(Socket socket) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("Recieved from client: " + reiceveMessage(socket));
                        System.out.println("Enter data to send");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void sendingDataThread(Socket socket){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    System.out.println("Write something to the client!");
                    try {
                        byte[] bytes = systemIn.readLine().getBytes();
                        for(Socket socket : sockets){
                            sendMessage(socket, bytes);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
    @Override
    public void run() {
        try {
            while (true) {
                Socket socket = server.accept();
                if(handshake(socket)){
                    sockets.add(socket);
                    listenerThread(socket);
                    sendingDataThread(socket);
                }

//                WebSocketReader webSocketReader = new WebSocketReader(socket);
//                Thread threadReader = new Thread(webSocketReader);
//                threadReader.start();
//                BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String line;
//                while((line = bf.readLine()) != null){
//                    System.out.println("Data: " + line);
//                }
//                System.out.println("Finish send data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean handshake(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        HashMap<String, String> keys = new HashMap<>();
        String str;

        //Reading client handshake
        while (!(str = in.readLine()).equals("")) {
            String[] s = str.split(": ");
            System.out.println();
            System.out.println(str);
            if (s.length == 2) {
                keys.put(s[0], s[1]);
            }
        }

        //Do what you want with the keys here, we will just use "Sec-WebSocket-Key"
        String hash;
        try {
            hash = new BASE64Encoder().encode(MessageDigest.getInstance("SHA-1").digest((keys.get("Sec-WebSocket-Key") + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return false;
        }

        //Write handshake response
        out.write("HTTP/1.1 101 Switching Protocols\r\n"
                + "Upgrade: websocket\r\n"
                + "Connection: Upgrade\r\n"
                + "Sec-WebSocket-Accept: " + hash + "\r\n"
                + "Origin: http://face2fame.com\r\n"
                + "\r\n");

        out.flush();

        return true;
    }

    private byte[] readBytes(Socket socket, int numOfBytes) throws IOException {
        byte[] b = new byte[numOfBytes];
        socket.getInputStream().read(b);
        return b;
    }
    private boolean convertAndPrintHeader(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String CaryOverDetection;
        // We must test byte 2 specifically for this. In the next step we add length bytes perhaps?
        //for(int i = 0; i < bytes.length; i++) {
        //}
        for (byte b : bytes) {
            CaryOverDetection = (String.format("%02X ", b));
            if (CaryOverDetection.contains("FE")){

                return false;
            }
            sb.append(String.format("%02X ", b));
        }
        System.out.println(sb.toString());
        return true;

    }

    private int getSizeOfPayload(byte b) {
        //Must subtract 0x80 from masked frames
        int a = b & 0xff;
        //System.out.println("PAYLOAD SIZE INT" + a);
        return ((b & 0xFF) - 0x80);
    }

    private void convertAndPrint(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        System.out.println(sb.toString());
    }

    private byte[] unMask(byte[] mask, byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] ^ mask[i % mask.length]);
        }
        return data;
    }

    public String reiceveMessage(Socket socket) throws IOException {
        byte[] buf = readBytes(socket,2);
        //dont use this byte[] buf2 = readBytes(4);
        int extendedsize = 0;
        System.out.println("Headers:");
        if (!convertAndPrintHeader(buf)) { // This means we detected an extended message
            String CaryOverDetectiona = new String("");
            byte[] bufadder1 = buf.clone();
            byte[] bufadder2 = readBytes(socket, 2);
            byte[] array1and2 = new byte[bufadder1.length + bufadder2.length];
            System.arraycopy(bufadder1, 0, array1and2, 0, bufadder1.length);
            System.arraycopy(bufadder2, 0, array1and2, bufadder1.length, bufadder2.length);
            for (byte b : array1and2) {
                CaryOverDetectiona = (String.format("%02X ", b));
                System.out.print(CaryOverDetectiona);
            }
            int i = ((array1and2[1] & 0xFF) - 0x80);
            //int c = ((array1and2[2] & 0xFF) - 0x80);
            //System.out.println(c+128);
            int j = ((array1and2[3] & 0xFF) - 0x80) + 2;
            //System.out.println("The size of this uber message is" + j);
            extendedsize = i + j;
            System.out.println("Extended Size is" + extendedsize);
            //System.exit(0);

        }

//convertAndPrint(buf2);// Check out the byte sizes
        int opcode = buf[0] & 0x0F;
        if (opcode == 8) {
            //Client want to close connection!
            System.out.println("Client closed!");
            socket.close();
            System.exit(0);
            return null;
        } else {
            int payloadSize = getSizeOfPayload(buf[1]);
            if (extendedsize >= 126) {
                payloadSize = extendedsize;
            }
            System.out.println("Payloadsize: " + payloadSize);
            buf = readBytes(socket, MASK_SIZE + payloadSize);
            System.out.println("Payload:");
            convertAndPrint(buf);
            buf = unMask(Arrays.copyOfRange(buf, 0, 4), Arrays.copyOfRange(buf, 4, buf.length));

            String message = new String(buf);

            return message;
        }
    }

    public void sendMessage(Socket socket,byte[] msg) throws IOException {
        System.out.println("Sending to client");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
        baos.write(SINGLE_FRAME_UNMASKED);
        baos.write(msg.length);
        baos.write(msg);
        baos.flush();
        baos.close();
        convertAndPrint(baos.toByteArray());
        os.write(baos.toByteArray(), 0, baos.size());
        os.flush();
    }
}
