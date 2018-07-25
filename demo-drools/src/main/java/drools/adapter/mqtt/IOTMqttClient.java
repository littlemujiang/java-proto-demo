/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.mqtt;

import drools.adapter.config.MqttConfig;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple to Introduction
 * className: IOTMqttClient
 *
 * @author mujiang
 * @version 2018/5/22 下午3:10
 */
@Data
public class IOTMqttClient {

    private static final Logger logger = LoggerFactory.getLogger(IOTMqttClient.class);

    public MqttClient mqttClient;
    private MqttConfig simulationConfig;

    public void initMqttClient(){
        try {
            this.mqttClient =  new MqttClient(simulationConfig.getHost(), MqttClient.generateClientId(), new MemoryPersistence());
            this.mqttClient.connect(getMqttConnectOptions(simulationConfig));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public Boolean sendMsg(String topic, String msg) {
        try {
            logger.info("Enter sendMsg topic={}", topic);
            MqttMessage data = new MqttMessage(msg.getBytes());
            data.setQos(1);
            mqttClient.publish(topic, data);
            return true;
        } catch (MqttException e) {
            logger.error("Failed to init sendMsg ", e);
        }
        return false;
    }

    private MqttConnectOptions getMqttConnectOptions(MqttConfig simulationConfig) {
        // MQTT的连接设置
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        //https://github.com/eclipse/paho.mqtt.java/issues/375
        mqttConnectOptions.setCleanSession(false);//设为false，如果此实例宕机，mosquitto可以继续存储消息
        // 设置连接的用户名
        mqttConnectOptions.setUserName(simulationConfig.getUsername());
        logger.info("username：" + simulationConfig.getUsername());
        // 设置连接的密码
        mqttConnectOptions.setPassword(simulationConfig.getPassword().toCharArray());
        logger.info("password：" + simulationConfig.getPassword());
        //使用自动重连机制
        mqttConnectOptions.setAutomaticReconnect(true);

        mqttConnectOptions.setMaxInflight(simulationConfig.getMaxInflight());
        // 设置超时时间 单位为秒
        mqttConnectOptions.setConnectionTimeout(simulationConfig.getConnectionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        mqttConnectOptions.setKeepAliveInterval(simulationConfig.getKeepAliveInterval());
        // 设置回调
        return mqttConnectOptions;
    }



}