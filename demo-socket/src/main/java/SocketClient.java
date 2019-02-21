/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Simple to Introduction
 * className: SocketClient
 *
 * @author mujiang
 * @version 2018/7/31 上午10:01
 */
public class SocketClient extends Thread {

    @Override
    public void run(){
//        Socket socket=new Socket("127.0.0.1",1888);
        try {
            Socket socket = new Socket("127.0.0.1",8765);
            System.out.println("客户端连接成功");
            Scanner scanner=new Scanner(System.in);
            BufferedWriter write=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));     //可用PrintWriter
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readline;
            while(true)                                                 //循环发消息
            {
                readline=scanner.nextLine();
                write.write(readline+'\n');                            //write()要加'\n'
                write.flush();
//			socket.shutdownOutput();
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        write.close();
//        in.close();
//        socket.close();
    }
    public static void main(String[] args) throws UnknownHostException, IOException
    {
        try {
            new SocketClient().start();

            Thread.sleep(10000);

            new SocketClient().start();

            Thread.sleep(10000);

            new SocketClient().start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}