package service.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
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
    @Value( "${mongodb.username}" )
    private String username;
    @Value( "${mongodb.source}" )
    private String source;
    @Value( "${mongodb.password}" )
    private String password;
    @Value( "${mongodb.serverAddress}" )
    private String serverAddress;

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
