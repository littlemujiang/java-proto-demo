package com.temp;/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


import com.config.MyNeo4jConfigeration;
import com.service.TDeviceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Simple to Introduction
 * className: com.temp.TDeviceServiceTest
 *
 * @author mujiang
 * @version 2018/4/3 上午10:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = {"com.service"})
@ContextConfiguration(classes={MyNeo4jConfigeration.class})
public class TDeviceServiceTest {

    @Autowired
    TDeviceService tDeviceService;

    @Test
    public void createDevice(){
        tDeviceService.createDevice();
    }

}