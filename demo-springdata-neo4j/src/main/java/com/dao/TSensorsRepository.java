/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.dao;

import com.model.TSensor;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Simple to Introduction
 * className: TSensorsRepository
 *
 * @author mujiang
 * @version 2018/4/3 上午10:25
 */
@Repository
public interface TSensorsRepository extends GraphRepository<TSensor> {


}