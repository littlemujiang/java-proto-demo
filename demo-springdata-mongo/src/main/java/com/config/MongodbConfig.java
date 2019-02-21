package com.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Created by breeze on 2017/1/12.
 */

@Configuration
public class MongodbConfig extends AbstractMongoConfiguration {


//    @Value( "${mongodb.username}" )
//    private String username;
//    @Value( "${mongodb.source}" )
//    private String source;
//    @Value( "${mongodb.password}" )
//    private String password;
//    @Value( "${mongodb.serverAddress}" )
//    private String serverAddress;

//local:
//    private String serverAddress="localhost";
//    private String source = "demo";
//    private String username;
//    private String password;

//MSP-dev
//    private String serverAddress="10.120.121.17";
//    private String source = "msp";
//    private String username = "mspadmin";
//    private String password = "abcd-1234";

//MSP-prod
//    private String serverAddress="10.96.83.25";
//    private String source = "msp";
//    private String username = "mspdev";
//    private String password = "abcd-1234";

//IOT-dev
//    private String serverAddress="10.76.3.70";
    private String serverAddress="10.76.3.66";
    private String source = "admin";
    private String username = null;
    private String password = null;



    @Override
    protected String getDatabaseName() {
        return source;
    }


//    @Bean
//    public Mongo mongo() throws Exception {
//        if(!StringUtils.isEmpty(username)||!StringUtils.isEmpty( password )){
//            List<MongoCredential> credentialsList=
//                    Arrays.asList(MongoCredential.createScramSha1Credential(
//                            username, source, password.toCharArray()));
//            return new MongoClient(serverAddressList(),credentialsList);
//        }
//        return new MongoClient(serverAddressList());
//    }
//
//
//    private List<ServerAddress> serverAddressList(){
//        ArrayList<ServerAddress> serverAddresses = new ArrayList<ServerAddress>( );
//        String[] split = serverAddress.split( "," );
//        for(int i=0;i<split.length;i++){
//            serverAddresses.add(new ServerAddress(split[i] , 27017));
//        }
//        return  serverAddresses;
//    }


    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
//        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
//        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
////                mongoTemplate.setWriteConcern(new WriteConcern("."));
//        converter.setMapKeyDotReplacement("!");

//        return new MongoTemplate(mongoDbFactory(), converter);
        return new MongoTemplate(mongoDbFactory(), mappingMongoConverter());

    }

    @Override
    @Bean
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(serverAddress, 27017);
//        return new MongoClient(singletonList(new ServerAddress(serverAddress, 27017)),
//                singletonList(MongoCredential.createCredential(username, source, password.toCharArray())));
    }


    @Bean
    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.setMapKeyDotReplacement("!");
        converter.afterPropertiesSet();

        //自定义转换器列表
        List<Converter> converters = new ArrayList<>();
//        converters.add(new DotToMarkConverter());

        CustomConversions conversions = new CustomConversions(converters);
        converter.setCustomConversions(conversions);

        return converter;
    }


}
