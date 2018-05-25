/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;


/**
 * Simple to Introduction
 * className: TSensor
 *
 * @author mujiang
 * @version 2018/4/3 上午10:18
 */

@Data
@NodeEntity(label =  "TSensor")
//@RequiredArgsConstructor()
public class TSensor {

    @GraphId
    private Long id;


//    @NonNull
    private String sensorId;


//    @NonNull
    private String sensorName;

    private String measure;

}
