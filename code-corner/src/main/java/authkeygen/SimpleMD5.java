package authkeygen;

import java.security.MessageDigest;

/**
 * Created by epcm on 2017/8/30.
 */
public class SimpleMD5 {


    public static String getMD5(String text, int length) {

        StringBuilder sb = new StringBuilder(40);

        try {

            MessageDigest MD5 = MessageDigest.getInstance("MD5");

            byte[] info_mid =  MD5.digest(text.getBytes());

            for(byte x : info_mid){

                if((x & 0xff)>>4 == 0) {
                    sb.append("0").append(Integer.toHexString(x & 0xff));
                } else {
                    sb.append(Integer.toHexString(x & 0xff));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(length == 16)
            return sb.toString().substring(8, 24);
        else
            return sb.toString();

    }


}
