package drools.adapter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by andyzhao on 2017/7/21.
 */
@Data
public class MqttConfig {


//    @Value("${mqtt.broker.host}")
//    private String host;
//    @Value("${mqtt.broker.username}")
//    private String username;
//    @Value("${mqtt.broker.password}")
//    private String password;
//    @Value("${mqtt.statistics.enable}")
//    private boolean mqttStatisticsEnable;
//
//    @Value("${mqtt.broker.maxInflight}")
//    private Integer maxInflight;
//    @Value("${mqtt.broker.connectionTimeout}")
//    private Integer connectionTimeout;
//
//    @Value("${mqtt.broker.keepAliveInterval}")
//    private Integer keepAliveInterval;

    private String host = "tcp://10.76.3.70:8883";

    private String username = "admin";

    private String password = "123456";

    private boolean mqttStatisticsEnable = true;

    private Integer maxInflight = 100000;

    private Integer connectionTimeout = 6000;

    private Integer keepAliveInterval = 120;

}
