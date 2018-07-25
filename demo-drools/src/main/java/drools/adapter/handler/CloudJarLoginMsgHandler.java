/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.handler;

import drools.adapter.domain.CloudJarMsg;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Simple to Introduction
 * className: CloudJarLoginMsgHandler
 *
 * @author mujiang
 * @version 2018/7/19 下午1:58
 */
public class CloudJarLoginMsgHandler extends AbstractMsgHandler{

    String deviceId;

    public CloudJarMsg device;

    public CloudJarLoginMsgHandler() {
        this.device = new CloudJarMsg();
    }



    @Override
    public void handleMsgBody(ArrayList<Integer> msgBody) {

        ArrayDeque<Integer> msgQue = new ArrayDeque<Integer>(msgBody);

        deviceId = parseDeviceId(msgQue);

        device.setDeviceId(deviceId);

        String mac = parseMac(msgQue);

        device.setMAC(mac);

    }

    @Override
    public byte[] buildResponse(){
// int[] loginMsg = {85,0,23,1,1,2,1,1,20,18,7,13,0,1,11,10,0,0,-56,-109,70,-58,77,-120,-87,-1};
        ArrayList<Integer> response = new ArrayList<Integer>();
//帧头
        response.add(85);
//消息头
        //消息长度：int2
        response.add(0);
        response.add(17);
        //消息版本号`
        response.add(1);

        //消息类型（登录响应：11）
        response.add(11);

//消息内容
        //设备id
        for(int i=0; i<13; i++){
            String ele = deviceId.substring(i * 2 , i * 2 + 2);
            response.add(Integer.valueOf(ele));
        }

//校验和
        int sum = 0;
        for(int i = 2; i < response.size(); i++){
            sum = sum + response.get(i);
        }
        response.add(sum);
//帧尾
        response.add(255);

        byte[] resp = new byte[response.size()];

        for(int i =0; i< response.size(); i++){
            resp[i] = (byte) response.get(i).intValue();
        }

        return resp;
    }



    private String parseMac(ArrayDeque<Integer> msgQue){


        return  "0050568543FF";

    }


}