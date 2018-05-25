/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com;

import com.service.TDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Simple of operate neo4j
 * className: EasyNeo4j
 *
 * @author mujiang
 * @version 2018/4/3 上午9:24
 */

@SpringBootApplication
public class EasyNeo4j {

    private static Logger logger = LoggerFactory.getLogger(EasyNeo4j.class);

    public static void main(String[] args) {

//        SpringApplication.run(EasyNeo4j.class, args);

        ApplicationContext ac = new AnnotationConfigApplicationContext(EasyNeo4j.class);
        TDeviceService ts = (TDeviceService) ac.getBean("TDeviceService");

        ts.createDevice();

//        ts.devicesQuery("CT040","MD030","CN030");
//        ts.devicesQueryTest("C01","M06", null);
//        ts.sensorFilter("C01", 10, 30);
        ts.deviceFilter("CT303","经度");
    }


}