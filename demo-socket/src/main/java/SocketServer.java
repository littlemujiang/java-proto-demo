/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple to Introduction
 * className: SocketServer
 *
 * @author mujiang
 * @version 2018/7/31 上午10:03
 */
public class SocketServer extends Thread{

    public void oneServer(ServerSocket server) throws IOException
    {
        String receive;
//        ServerSocket server=new ServerSocket(1888);

        System.out.println("服务器启动成功");
        Socket socket=server.accept();
        System.out.println("获得连接");
        new SocketProcesserThread(socket).start();
        System.out.println("处理线程已启动");
//        writer.close();
//        in.close();
//        socket.close();
//        server.close();
    }

    @Override
    public void run() {
        try {
            ServerSocket server=new ServerSocket(8765);
            while (true){
                oneServer(server);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
//        new SocketServer().oneServer();
//        new SocketServer().oneServer();
        new Thread(new SocketServer()).start();
//        new Thread(new SocketServer()).start();
    }



    public class SocketProcesserThread extends Thread{
        private Socket socket = null;

        SocketProcesserThread(Socket socket){
            this.socket = socket;
        }
        String receive;

        @Override
        public void run() {
                        //'\n'一定要加
                try {
                    BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    while(true)                                                    //一直等客户端的消息
                    {
                        receive=in.readLine();
                        System.out.println("客户端发来:"+receive);
                        writer.write("服务器接收到了:"+receive +'\n');
                        writer.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}