/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.model;

import lombok.Data;
import org.json.JSONObject;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple to Introduction
 * className: TDevice
 *
 * @author mujiang
 * @version 2018/4/3 上午10:14
 */

@Data
@NodeEntity(label =  "TDevice")
public class TDevice {


    @GraphId
    private Long id;

    @Property
    private String deviceId;

    @Property(name="name")
    private String deviceName;

    @Property
    private String categoryId;

    @Property
    private String modelId;

    @Property
    private String connectorId;

    @Property
    private int sensorNum;


    //使用外部定义的关系
    @Relationship(type = "DEVICE_SENSOR")
    private List<TSensor> sensors;

//    private Map<String , Object> map;

    @Convert(ParamConverter.class)
    private Param paramJson;

//    //使用外部定义的关系
//    @Relationship(type = "DEVICE_SENSOR")
//    private List<TDeviceSensorRelation> relations;

}