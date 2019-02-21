/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter;

import drools.adapter.handler.AbstractMsgHandler;
import drools.adapter.handler.CloudJarDataMsgHandler;
import drools.adapter.handler.CloudJarLoginMsgHandler;
import drools.adapter.handler.MicroClimateDataMsgHandler;
import drools.adapter.mqtt.IOTMqttClient;
import drools.adapter.util.ParseUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Simple to Introduction
 * className: MicroClimateAdapter
 *
 * @author mujiang
 * @version 2018/7/19 上午11:15
 */

@Data
public class CloudJarAdapter {

    private int frameHeader;

    private int msgLength;
    private int msgVersion;
    private int msgType;
    private ArrayList<Integer> msgBody = new ArrayList<Integer>();

    private int checkSum;

    private int frameEnd;

    public ArrayList<Integer> resp;

    private IOTMqttClient mqttClient = new IOTMqttClient();

    private void splitMsg(int[] msg){

//        List<Integer> msgL =  Arrays.asList(loginMsg);

        frameHeader = msg[0];
        msgLength = ParseUtil.combineData(msg[1], msg[2]);
        msgVersion = msg[3];
        msgType = msg[4];
        for(int i = 5; i < msg.length-2 ; i++ ){
            msgBody.add(msg[i]);
        }
        checkSum = msg[msg.length-2];
        frameEnd = msg[msg.length-1];
    }


    public void handleMsg(int[] msgOrigin){

        splitMsg(msgOrigin);

        handleBody(msgBody);

    }

    private void handleBody(ArrayList<Integer> msgBody){

        AbstractMsgHandler msgHandler = null;

        switch (msgType){
            case 1:
                msgHandler = new CloudJarLoginMsgHandler();
                break;
            case 2:
                msgHandler = new CloudJarDataMsgHandler();
                break;

        }

        msgHandler.handleMsgBody(msgBody);
        resp = msgHandler.buildResponse();
//        msgHandler.send2IOT(mqttClient);
//        resp = msgHandler.buildResponse();

    }


    public static void main(String[] args) {

        String dataMsg = "[55, 00, 44, 01, 02, 05, 02, 03, 14, 12, 01, 06, 00, 01, 10, 10, 00, 00, 01, 02, 12, 07, 1f, 09, 21, " +
                "24, 01, 27, 36, 00, 00, 10, e2, 74, 1e, 00, 00, 6b, 62, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 02, " +
                "f7, 03, 15, 00, 00, 00, cf, 00, 01, 86, 9c, 00, 00, 00, 00, 00, 75, 4f, ff]";

        String dataMsg2 = "[55, 00, 44, 01, 02, 05, 02, 03, 14, 12, 01, 06, 00, 01, 10, 10, 00, 00, 01, 02, 12, 07, 1f, 12, " +
                "13, 0f, 01, 27, 36, 00, 00, 07, 0a, 74, 1e, 00, 00, 61, 8a, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, " +
                "00, 02, ce, 03, 0b, 00, 00, 00, 33, 00, 01, 85, 9e, 00, 00, 00, 00, 00, 75, a4, ff]";

        String loginMsg  =
                "[55, 00, 17, 01, 01, 02, 01, 01, 14, 12, 07, 0d, 00, 02, 0b, 0a, 00, 00, c8, 93, 46, c6, 4c, bd, de, ff]";

        String loginMsg2  ="[55, 00, 17, 01, 01, 02, 01, 01, 14, 12, 07, 0d, 00, 01, 0b, 0a, 00, 00, c8, 93, 46, c6, 4d, 88, a9, ff]";

        CloudJarAdapter cjm = new CloudJarAdapter();

        cjm.handleMsg(ParseUtil.dataPreProccess(loginMsg2));

        System.out.println("done");

        System.out.println(cjm);

    }


}