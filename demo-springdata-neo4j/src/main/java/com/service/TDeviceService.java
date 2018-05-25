/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.service;
import com.model.Param;
import com.model.TDevice;
import com.dao.TDeviceRepository;
import com.model.TSensor;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.ogm.response.model.NodeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Simple to Introduction
 * className: TDeviceService
 *
 * @author mujiang
 * @version 2018/4/3 上午10:31
 */

@Service
public class TDeviceService {


    private static Logger logger = LoggerFactory.getLogger(TDeviceService.class);

    @Autowired
    TDeviceRepository tDeviceRepository;




    @Transactional
    public void createDevice(){

        try {
            for(int i=0; i<1 ;i ++){
                TDevice td1 = new TDevice();
                TSensor sensor = new TSensor();
                sensor.setSensorId(UUID.randomUUID().toString());
                sensor.setSensorName("nnn");

                TSensor sensor2 = new TSensor();
                sensor2.setSensorId(UUID.randomUUID().toString());
                sensor2.setSensorName("mmm");

                List<TSensor> sensors = new ArrayList<TSensor>();
                sensors.add(sensor);
                sensors.add(sensor2);

                td1.setDeviceId( UUID.randomUUID().toString() );
                td1.setDeviceName("tDevice" + i);

                td1.setCategoryId("CT303");

                td1.setModelId("MD050");
                td1.setConnectorId("CN050");
                td1.setSensorNum( i * 5 );
                td1.setSensors(sensors);

//                JSONObject json = new JSONObject();
                Param json = new Param();
                json.put("_latitude", "经度");
                json.put("_longitude", "纬度");
                json.put("_brand", "pinpai");

                td1.setParamJson(json);

                tDeviceRepository.save(td1);
//            tSensorsRepository.save(sensor);
//            tDeviceSensorRelationRepository.save(relation);
            }

            logger.info("* * * * * * * * * * * * * * * *");
            logger.info("create device done");
            logger.info("* * * * * * * * * * * * * * * *");
        }catch (Exception e){
            logger.error(e.getMessage());
        }



    }

    public void devicesQuery(String ctId, String mdId, String cnId){

//        List<TDevice>  devices = tDeviceRepository.findByCategoryIdOrModelIdOrConnectorId(ctId,mdId,cnId);

        List<TDevice>  devices = tDeviceRepository.findByCategoryIdAndModelIdAndConnectorId(ctId,mdId,cnId);

        logger.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        logger.info("query device by query: " );
        logger.info(String.valueOf(devices.get(0).getSensors().size()));
        logger.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

    }

    public void devicesQueryTest(String ctId, String mdId, String cnId){
        TDevice device = new TDevice();
        device.setCategoryId(ctId);
        device.setModelId(mdId);
        device.setConnectorId(cnId);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIncludeNullValues();

        Example<TDevice> deviceExample = Example.of(device, matcher);

        List<TDevice>  devices = tDeviceRepository.findByCategoryIdOrModelIdOrConnectorId(ctId,mdId,cnId);
//        List<TDevice>  devices = tDeviceExampleRepository.findAll(deviceExample);
        logger.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        logger.info("query device by notNull: " );
        logger.info(devices.toString());
        logger.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

    }

    public void sensorFilter(String ctId, int begin, int end){
        List<TDevice>  devices = tDeviceRepository.findByCategoryIdAndSensorNumBetween(ctId,begin,end);
        logger.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        logger.info("filter by sensorNum: " );
        logger.info(devices.toString());
        logger.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void deviceFilter(String ctId, String param){
        try {
            JSONObject paramJSON = new JSONObject();
            paramJSON.put("latitude",param);
            paramJSON.put("longitude", "纬度");
//            TDevice  devices = tDeviceRepository.findByCategoryId(ctId);
            TDevice devices = tDeviceRepository.findByCategoryId(ctId);
            logger.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
            logger.info("query result: " );
            logger.info(devices.toString());
            logger.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        }catch (JSONException e){
            logger.error(e.getMessage());
        }

    }



}