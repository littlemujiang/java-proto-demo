package com.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private String serverAddress="10.96.83.25";
    private String source = "msp";
    private String username = "mspdev";
    private String password = "abcd-1234";

    @Override
    protected String getDatabaseName() {
        return source;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        if(!StringUtils.isEmpty(username)||!StringUtils.isEmpty( password )){
            List<MongoCredential> credentialsList=
                    Arrays.asList(MongoCredential.createScramSha1Credential(
                            username,source, password.toCharArray()));
            return new MongoClient(serverAddressList(),credentialsList);
        }
        return new MongoClient(serverAddressList());
    }


    private List<ServerAddress> serverAddressList(){
        ArrayList<ServerAddress> serverAddresses = new ArrayList<ServerAddress>( );
        String[] split = serverAddress.split( "," );
        for(int i=0;i<split.length;i++){
            serverAddresses.add(new ServerAddress(split[i] , 27017));
        }
        return  serverAddresses;
    }
}
