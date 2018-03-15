package ioc;

import org.springframework.stereotype.Component;

/**
 * Created by mujiang on 2017/10/31.
 */

@Component
public class CdMedia implements IMedia {

    public void play(){

        System.out.println("CD media is playing.");

    }


}
