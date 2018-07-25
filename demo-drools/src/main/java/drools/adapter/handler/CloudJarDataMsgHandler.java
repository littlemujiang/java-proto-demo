/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.handler;

import drools.adapter.domain.CloudJarMsg;
import drools.adapter.util.ParseUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;

/**
 * Simple to Introduction
 * className: CloudJarLoginMsgHandler
 *
 * @author mujiang
 * @version 2018/7/19 下午1:58
 */
public class CloudJarDataMsgHandler extends AbstractMsgHandler{

    public CloudJarMsg device;

    public CloudJarDataMsgHandler() {
        this.device = new CloudJarMsg();
    }

    @Override
    public void handleMsgBody(ArrayList<Integer> msgBody) {
        ArrayDeque<Integer> msgQue = new ArrayDeque<Integer>(msgBody);

        //取 设备id
        String deviceId = parseDeviceId(msgQue);

        device.setDeviceId(deviceId);

        parseData(msgQue);

    }


    private String parseData(ArrayDeque<Integer> msgQue){

        //取 报送数据量
        int msgNum = msgQue.pop();

        //取 状态 并格式化
        int statusInt = msgQue.pop();
        int[] status = new int[8];

        for(int i = 7; i >= 0; i--){
            status[7-i] = statusInt >>> i & 1;
//            System.out.print(statusInt >>> i & 1);
        }


        //逐条取出数据
        for(int i=0; i < msgNum; i++){
            //取 设备检测时间
            Date msgDate = parseDate(msgQue);

            //取 传感器数据
            parseSensorData(msgQue);

        }

        return  "";

    }

    private void parseSensorData(ArrayDeque<Integer> msgQue){

        int humidity = ParseUtil.combineData(msgQue.pop(), msgQue.pop());
        int temperature = ParseUtil.combineData(msgQue.pop(), msgQue.pop());
        int formaldehyde = ParseUtil.combineData(msgQue.pop(), msgQue.pop());
        int pm25 = ParseUtil.combineData(msgQue.pop(), msgQue.pop());
        int co = ParseUtil.combineData(msgQue.pop(), msgQue.pop());
        int co2 = ParseUtil.combineData(msgQue.pop(), msgQue.pop());

        System.out.println(temperature);

    }


}