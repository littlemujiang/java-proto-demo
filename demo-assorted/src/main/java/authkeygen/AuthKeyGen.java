package authkeygen;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Timestamp;

/**
 * Created by epcm on 2017/8/29.
 */
public class AuthKeyGen {

    private static final Logger LOG = Logger.getLogger(AuthKeyGen.class);

    //根据 client_id 和 client_secret生成 authkey
    public String getAuthKey(String client_id, String client_secret) {

        Timestamp now = new Timestamp(System.currentTimeMillis());

        String authInfo_plain = client_id + client_secret + "AT" + now.getTime();

        String authInfo_md5 = SimpleMD5.getMD5(authInfo_plain, 16);

//        LOG.info(authInfo_md5);

        return authInfo_md5 + "." + now.getTime() ;


    }



    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

        String log4jPath = AuthKeyGen.class.getClassLoader().getResource("").getPath()+"log4j.properties";
        PropertyConfigurator.configure(log4jPath);

        System.out.println(log4jPath);

//        LOG.info("~~~~~~~~~~~~~");

        String appKey  = "3466927A3BE24B398C0D4E9AE9B66C14";
        System.out.println("MSP-AppKey : " + appKey);

        String client_id = "E682F8D631C84C45AA0A0A9B4526395E";
        String client_secret = "D35A4A550D53448EB72FD71BF78820AA";


        AuthKeyGen akg = new AuthKeyGen();
        String authKey = akg.getAuthKey(client_id ,client_secret);

        System.out.println("MSP-AuthKey: " + authKey);
    }


}
