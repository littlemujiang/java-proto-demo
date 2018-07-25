/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.handler;


import drools.adapter.config.MqttConfig;
import drools.adapter.domain.MicroClimateMsg;
import drools.adapter.mqtt.IOTMqttClient;
import drools.adapter.util.ParseUtil;
import lombok.Data;
import org.bson.Document;

import java.sql.Timestamp;
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
@Data
public class MicroClimateDataMsgHandler extends AbstractMsgHandler {

    MicroClimateMsg deviceMsg;

    String topic =  "a_6pgqs/6ph5h/p_6phX7";

    public MicroClimateDataMsgHandler() {
        deviceMsg = new MicroClimateMsg();
    }

    @Override
    public void handleMsgBody(ArrayList<Integer> msgBody) {
        ArrayDeque<Integer> msgQue = new ArrayDeque(msgBody);

        //取 设备id
        String deviceId = parseDeviceId(msgQue);
        deviceMsg.setDeviceId(deviceId);
        parseData(msgQue);

    }

    @Override
    public void send2IOT(IOTMqttClient mqttClient){
        if(mqttClient == null){
            mqttClient = new IOTMqttClient();
        }
        if(mqttClient.getMqttClient() == null || !mqttClient.getMqttClient().isConnected() ){
            mqttClient.setSimulationConfig(new MqttConfig());
            mqttClient.initMqttClient();
        }

        //发送位置数据
//        mqttClient.sendMsg("","");

        //发送采集数据

        Document dataDocument = collectData();
        Document parentDocument = new Document();
        parentDocument.put("deviceid",deviceMsg.getDeviceId());
        parentDocument.put("topic", topic);

        Document msgDocument = new Document();
        msgDocument.put("data", dataDocument);
        parentDocument.put("msg",msgDocument);
//        parentDocument.put("ts", deviceMsg.getMsgDate().getTime());
        parentDocument.put("ts", String.valueOf(System.currentTimeMillis()));
        String msg = parentDocument.toJson();
        mqttClient.sendMsg(topic, msg);
    }

    private Document collectData(){
        Document dataDocument = new Document();
        dataDocument.put("status", deviceMsg.getStatus());
        dataDocument.put("rain5Min", deviceMsg.getRain5Min());
        dataDocument.put("rain24Hour", deviceMsg.getRain24Hour());
        dataDocument.put("rainLastDay", deviceMsg.getRainLastDay());
        dataDocument.put("humidity", deviceMsg.getHumidity());
        dataDocument.put("temperature", deviceMsg.getTemperature());
        dataDocument.put("illumination", deviceMsg.getIllumination());
        dataDocument.put("airPressure", deviceMsg.getAirPressure());
        dataDocument.put("windSpeed", deviceMsg.getWindSpeed());
        dataDocument.put("windDirection", deviceMsg.getWindDirection());
        dataDocument.put("cellVoltage", deviceMsg.getCellVoltage());
        return dataDocument;
    }

    private String parseData(ArrayDeque<Integer> msgQue){

        //取 报送数据量
        int msgNum = msgQue.pop();
        //取 状态
        deviceMsg.setStatus(msgQue.pop());
        //逐条取出数据
        for(int i=0; i < msgNum; i++){
            //取 设备检测时间
            Date msgDate = parseDate(msgQue);
            deviceMsg.setMsgDate(msgDate);
            //取 传感器数据
            parseSensorData(msgQue);
        }
        return  "";
    }

    private void parseSensorData(ArrayDeque<Integer> msgQue){
        //取位置信息
        deviceMsg.setGeographic( msgQue.pop() );

//        int latitudeFlag = 1;
        int latitude = msgQue.pop();
        int latitudeMin = msgQue.pop();
        int latitudeSec = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop(), msgQue.pop(), msgQue.pop());
//        if(latitudeSec < 0){
//            latitudeSec = -latitudeSec;
//            latitudeFlag *= -1;
//        }
        deviceMsg.setLatitude( latitude + (latitudeMin + Double.valueOf( "0."+String.valueOf(latitudeSec)) ) / 60 );

//        int longitudeFlag = 1;
        int longitude = msgQue.pop();
        int longitudeMin = msgQue.pop();
        int longitudeSec = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop(), msgQue.pop(), msgQue.pop());
//        if(longitudeSec < 0){
//            longitudeSec = -longitudeSec;
//            longitudeFlag *= -1;
//        }
        deviceMsg.setLongitude( longitude + (longitudeMin + Double.valueOf( "0."+String.valueOf(longitudeSec)) ) / 60);

//取测量数据

        int rain5Min = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop(), msgQue.pop(), msgQue.pop());
        deviceMsg.setRain5Min(rain5Min/10.0);
        int rain24Hour = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop(), msgQue.pop(), msgQue.pop());
        deviceMsg.setRain24Hour(rain24Hour/10.0);
        int rainLastDay = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop(), msgQue.pop(), msgQue.pop());
        deviceMsg.setRainLastDay(rainLastDay/10.0);

        //湿度
        int humidity = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop());
        deviceMsg.setHumidity(humidity/10.0);
        //温度
        int temperature = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop());
        deviceMsg.setTemperature(temperature/10.0);
        //照度
        int illumination = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop(), msgQue.pop(), msgQue.pop());
        deviceMsg.setIllumination(illumination);
        //气压
        int airPressure = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop(), msgQue.pop(), msgQue.pop());
        deviceMsg.setAirPressure(airPressure);
        //风速
        int windSpeed = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop());
        deviceMsg.setWindSpeed(windSpeed/10.0);
        //风向
        int windDirection = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop());
        deviceMsg.setWindDirection(windDirection/10.0);
        //电池电压
        int cellVoltage = ParseUtil.combineData4Location(msgQue.pop(), msgQue.pop());
        deviceMsg.setCellVoltage(cellVoltage/10.0);

        System.out.println(deviceMsg);
    }


}