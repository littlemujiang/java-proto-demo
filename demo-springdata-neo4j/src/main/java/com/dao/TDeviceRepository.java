/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.dao;

import com.model.ParamConverter;
import com.model.TDevice;
import org.json.JSONObject;
//import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Simple to Introduction
 * className: DeviceRepository
 *
 * @author mujiang
 * @version 2018/4/3 上午10:21
 */

@Repository
public interface TDeviceRepository extends GraphRepository<TDevice> {

    public List<TDevice> findByDeviceId(String deviceId);

    public List<TDevice> findByCategoryIdAndModelIdAndConnectorId(String categoryId, String modelId, String connectorId);
//    public List<TDevice> findOptionalCategoryIdAndModelIdAndConnectorId(String categoryId, String modelId, String connectorId);

    public List<TDevice> findByCategoryIdOrModelIdOrConnectorId(String categoryId, String modelId, String connectorId);

//    public List<TDevice> findByCategoryIdNotNullAndModelIdNotNullAndConnectorIdNotNull(String categoryId, String modelId, String connectorId);

    public List<TDevice> findByCategoryIdAndSensorNumBetween(String categoryId, int begin, int end);


    public List<TDevice> findByCategoryId(String categoryId, String modelId);

    TDevice findByCategoryIdAndSensors_SensorName(String categoryId, String sensorName);

    TDevice findByCategoryId(String categoryId );
//    Map<String,Object> findByCategoryIdAndParamJsonContains(String categoryId, String latitude);


}

