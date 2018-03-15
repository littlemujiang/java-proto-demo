package com;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mujiang on 2017/9/12.
 */
public class Sender {

    private final static String QUEUE_NAME = "hello";

    private Channel channel;
    private Connection connection;

    private Channel getChannel() throws Exception{

        if(channel!=null)
            return channel;
        else {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
            return channel;
        }

    }



    private void sentMsg(String queue_name, String message) {

        try {
            Channel channel = getChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    //        message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

//            channel.close();
//            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void sentEsc() throws IOException, TimeoutException {

        channel.close();
        connection.close();

    }




    public static void main(String[] argv) {

        Sender sender = new Sender();

        try {

            for (int i=0; i<10; i++){

                sender.sentMsg(QUEUE_NAME, "message: "+i);
                TimeUnit.MILLISECONDS.sleep(100);

            }
            sender.sentEsc();

        }catch (Exception e){
            e.printStackTrace();
        }




    }


}
