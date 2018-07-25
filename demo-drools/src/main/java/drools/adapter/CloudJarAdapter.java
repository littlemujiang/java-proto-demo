/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter;

import drools.adapter.handler.AbstractMsgHandler;
import drools.adapter.handler.CloudJarDataMsgHandler;
import drools.adapter.handler.CloudJarLoginMsgHandler;
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
public class CloudJarAdapter {

    private int frameHeader;

    private int msgLength;
    private int msgVersion;
    private int msgType;
    private ArrayList<Integer> msgBody = new ArrayList<Integer>();

    private int checkSum;

    private int frameEnd;

    public byte[] resp;

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

    private void handleMsg(){

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

    }





    public static void main(String[] args) {

        int[] loginMsg = {85, 0, 23, 1, 1, 2, 1, 1, 20, 18, 7, 13, 0, 1, 11, 10, 0, 0, -56, -109, 70, -58, 77, -120, -87, -1};
        String loginMsgString = "[85,0,23,1,1,2,1,1,20,18,7,13,0,1,11,10,0,0,-56,-109,70,-58,77,-120,-87,-1]";
//        int[] dataMsg  = {85,0,68,1,2,5,2,3,20,18,1,6,0,1,16,16,0,0,1,2,18,7,16,19,7,7,1,39,54,0,0,8,-12,116,30,0,0,99,116,0,0,0,0,0,0,0,0,0,0,0,0,2,-12,3,2,0,0,0,0,0,1,-122,75,0,0,0,0,0,117,-15,-1};
        int[] dataMsg  = {
                85,
        0,
                68,
                1,
                2,
                5,
                2,
                3,
                20,
                18,
                1,
                6,
                0,
                1,
                16,
                16,
                0,
                0,
                1,
                2,
                18,
                7,
                23,
                17,
                23,
                36,
                1,
                39,
                54,
                0,
                0,
                13,
                -128,
                116,
                30,
                0,
                0,
                104,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                2,
                -55,
                3,
                9,
                0,
                0,
                0,
                63,
                0,
                1,
                -123,
                -110,
                0,
                0,
                0,
                0,
                0,
                117,
                -90,
                -1
    } ;

        CloudJarAdapter cjm = new CloudJarAdapter();
//        cjm.splitMsg(loginMsg);
        cjm.splitMsg(dataMsg);
        cjm.handleMsg();




        System.out.println(cjm);



    }


}