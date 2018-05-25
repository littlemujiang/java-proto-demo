/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.neo4j.ogm.config.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Simple to Introduction
 * className: MyNeo4jConfigeration
 *
 * @author mujiang
 * @version 2018/4/3 上午9:38
 */

@org.springframework.context.annotation.Configuration
@EnableNeo4jRepositories(basePackages = "com.dao")
@EnableTransactionManagement
public class MyNeo4jConfigeration {


//    @Bean
//    public org.neo4j.ogm.config.Configuration configuration() {
//        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
//                .uri("bolt://10.76.3.70:7687")
////                .uri("http://10.76.3.70:7474")
//                .credentials("neo4j", "neo4j123")
//                .build();
//        return configuration;
//    }


    @Bean
    public Configuration configuration() {
        Configuration config = new Configuration();
        config.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI("http://neo4j:neo4j123@10.76.3.70:7474");
        return config;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(),"com.model" );
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

}