/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.handler;

import drools.adapter.domain.AbstractDeviceMsg;
import drools.adapter.domain.DeviceId;
import drools.adapter.mqtt.IOTMqttClient;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;

/**
 * Simple to Introduction
 * className: AbstractMsgHandler
 *
 * @author mujiang
 * @version 2018/7/19 下午1:58
 */
@Data
public class AbstractMsgHandler {

    public DeviceId deviceID = new DeviceId();

    public AbstractDeviceMsg deviceMsg;


    public AbstractMsgHandler() {
        deviceMsg = new AbstractDeviceMsg();
    }

    public void handleMsgBody(ArrayList<Integer> msgBody){

    }

    public void send2IOT(IOTMqttClient mqttClient){

    }



    public ArrayList<Integer>  buildResponse( ){
        return null;
    }

    public String parseDeviceId(ArrayDeque<Integer> msgQue){

        deviceID.setCheckType(String.format("%02d", msgQue.pop()));
        deviceID.setType(String.format("%02d", msgQue.pop()));
        deviceID.setSubType(String.format("%02d", msgQue.pop()));
        deviceID.setProductionDate( String.format("%02d",msgQue.pop()) + String.format("%02d",msgQue.pop())+ String.format("%02d",msgQue.pop())+ String.format("%02d",msgQue.pop()));
        deviceID.setProductionSerial(String.format("%02d", msgQue.pop()) + String.format("%02d",msgQue.pop()));
        deviceID.setSoftVersion(String.format("%02d", msgQue.pop()));
        deviceID.setHardVersion(String.format("%02d", msgQue.pop()));
        deviceID.setReserved1(String.format("%02d", msgQue.pop()));
        deviceID.setReserved2(String.format("%02d", msgQue.pop()));

        System.out.println(deviceID.toString());
        return deviceID.toString();
    }

    public Date parseDate(ArrayDeque<Integer> msgQue){

        Date date = new Date();
        String yy = String.format("%02d",msgQue.pop());
        String MM = String.format("%02d",msgQue.pop());
        String dd = String.format("%02d",msgQue.pop());
        String HH = String.format("%02d",msgQue.pop());
        String mm = String.format("%02d",msgQue.pop());
        String ss = String.format("%02d",msgQue.pop());

        String string = yy + "-" + MM + "-" + dd +" " + HH + ":" + mm + ":" + ss;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            date = sdf.parse(string);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}