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

//        String authInfo_plain = client_id + client_secret + "AT" + now.getTime();
        String authInfo_plain = client_id + client_secret + "AT" + "1505358271580";

        String authInfo_md5 = SimpleMD5.getMD5(authInfo_plain, 16);

//        LOG.info(authInfo_md5);

//        return authInfo_md5 + "." + now.getTime() ;
        return authInfo_md5 + "." + "1505358271580" ;


    }



    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

        String log4jPath = AuthKeyGen.class.getClassLoader().getResource("").getPath()+"log4j.properties";
        PropertyConfigurator.configure(log4jPath);

        System.out.println(log4jPath);

//        LOG.info("~~~~~~~~~~~~~");

        String appKey  = "DB4CB01EF7AF4F8F8CA08D3CD8444C57";
        System.out.println("MSP-AppKey : " + appKey);

        String client_id = "69B6B0479B31443D8D35D904E2D0B23B";
        String client_secret = "4E85B038CFB949928F820294AD05CD40";


        AuthKeyGen akg = new AuthKeyGen();
        String authKey = akg.getAuthKey(client_id ,client_secret);

        System.out.println("MSP-AuthKey: " + authKey);
    }


}
