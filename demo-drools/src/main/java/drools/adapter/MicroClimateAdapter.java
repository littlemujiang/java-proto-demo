/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter;

import drools.adapter.handler.AbstractMsgHandler;
import drools.adapter.handler.MicroClimateDataMsgHandler;
import drools.adapter.mqtt.IOTMqttClient;
import drools.adapter.util.ParseUtil;
import lombok.Data;

import java.util.ArrayList;

/**
 * Simple to Introduction
 * className: MicroClimateAdapter
 *
 * @author mujiang
 * @version 2018/7/19 上午11:15
 */

@Data
public class MicroClimateAdapter {

    private int frameHeader;

    private int msgLength;
    private int msgVersion;
    private int msgType;
    private ArrayList<Integer> msgBody = new ArrayList<Integer>();

    private int checkSum;

    private int frameEnd;

    public byte[] resp;

    private IOTMqttClient mqttClient = new IOTMqttClient();


    public void handleMsg(int[] msgOrigin){

        splitMsg(msgOrigin);

        handleBody(msgBody);

    }

    private void splitMsg(int[] msg){

//        List<Integer> msgL =  Arrays.asList(loginMsg);

        frameHeader = msg[0];
        msgLength = drools.adapter.util.ParseUtil.combineData(msg[1], msg[2]);
        msgVersion = msg[3];
        msgType = msg[4];
        for(int i = 5; i < msg.length-2 ; i++ ){
            msgBody.add(msg[i]);
        }
        checkSum = msg[msg.length-2];
        frameEnd = msg[msg.length-1];
    }

    private void handleBody(ArrayList<Integer> msgBody){

        AbstractMsgHandler msgHandler = null;

        switch (msgType){
            case 2:
                msgHandler = new MicroClimateDataMsgHandler();
                msgHandler.handleMsgBody(msgBody);
                msgHandler.send2IOT(mqttClient);
                break;


        }

//        resp = msgHandler.buildResponse();

    }



    public int[] dataPreHandler(String msg){
        String[] msgStr = msg.substring(1, msg.length()-1).split(",");

        int[] msgInt = new int[msgStr.length];

        for(int i=0; i<msgStr.length; i++){
            msgInt[i] = Integer.parseInt(msgStr[i].trim(), 16);
        }
        return msgInt;
    }



    public static void main(String[] args) {

//        int[] dataMsg  = {85,0,68,1,2,5,2,3,20,18,1,6,0,1,16,16,0,0,1,2,18,7,16,19,7,7,1,39,54,0,0,8,-12,116,30,0,0,99,116,0,0,0,0,0,0,0,0,0,0,0,0,2,-12,3,2,0,0,0,0,0,1,-122,75,0,0,0,0,0,117,-15,-1};

        String msg  = "[55, 00, 44, 01, 02, 05, 02, 03, 14, 12, 01, 06, 00, 01, 10, 10, 00, 00, 01, 02, 12, 07, 19, 0f, 26, 25, 01, 27, 36, 00, 00, 10, 6b, 74, 1e, 00, 00, 6a, eb, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 02, 9f, 03, 02, 00, 00, 00, 8c, 00, 01, 84, 9a, 00, 00, 00, 00, 00, 75, b4, ff]";
        String msg2  = "[55, 00, 17, 01, 01, 02, 01, 01, 14, 12, 07, 0d, 00, 01, 0b, 0a, 00, 00, c8, 93, 46, c6, 4d, 88, a9, ff]";
        MicroClimateAdapter cjm = new MicroClimateAdapter();

        cjm.handleMsg(ParseUtil.dataPreProccess(msg2));

        System.out.println("done");

//        System.out.println(Integer.parseInt("ff", 16));
//        System.out.println(String.format("%02x",255));

    }


}