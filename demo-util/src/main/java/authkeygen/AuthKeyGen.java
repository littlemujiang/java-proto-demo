package authkeygen;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by epcm on 2017/8/29.
 */

public class AuthKeyGen {

    private static final Logger LOG = Logger.getLogger(AuthKeyGen.class);

    //根据 client_id 和 client_secret生成 authkey
    public String getAuthKey(String client_id, String client_secret) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        String timestamp = String.valueOf(now.getTime());
        return getAuthKeyWithTime(client_id, client_secret, timestamp) ;

    }

    public String getAuthKeyWithTime(String client_id, String client_secret, String timestamp){

        String authInfo_plain = client_id + client_secret + "AT" + timestamp;
        String authInfo_md5 = SimpleMD5.getMD5(authInfo_plain, 16);
        return authInfo_md5 + "." + timestamp ;

    }

    public Map getYMLInfo(String filename){

        try {
            Yaml yaml = new Yaml();
            URL url = AuthKeyGen.class.getClassLoader().getResource(filename);
            if (url != null) {
                Map map =(Map)yaml.load(new FileInputStream(url.getFile()));
//                System.out.println(map);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

        String log4jPath = AuthKeyGen.class.getClassLoader().getResource("").getPath()+"log4j.properties";
        PropertyConfigurator.configure(log4jPath);

//        System.out.println(log4jPath);

//        LOG.info("~~~~~~~~~~~~~");

        AuthKeyGen akg = new AuthKeyGen();

        Map allAuthInfo = akg.getYMLInfo("clientInfo.yml");

        Map spcAuthInfo = (Map) allAuthInfo.get("haiyang");
//        Map spcAuthInfo = (Map) allAuthInfo.get("mydemo");

        String appKey  = (String) spcAuthInfo.get("appKey");
        String client_id = (String) spcAuthInfo.get("client_id");
        String client_secret = (String) spcAuthInfo.get("client_secret");

        String authKey = akg.getAuthKey(client_id ,client_secret);

        System.out.println("MSP-AppKey : " + appKey);
        System.out.println("MSP-AuthKey: " + authKey);

    }




}
