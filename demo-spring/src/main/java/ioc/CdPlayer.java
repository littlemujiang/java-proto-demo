package ioc;

import aop.AopConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by mujiang on 2017/10/31.
 */
@Component
public class CdPlayer implements IPlayer {

    private IMedia iMedia;

    @Autowired
    public void loadMedia(IMedia iMedia) {
        this.iMedia = iMedia;
    }

    public void playMedia(){

        iMedia.play();

    }


    @Autowired
    IMedia iMediaAuto;

    private void playMediaAuto(){

        iMediaAuto.play();

    }


    public static void main(String[] args){

        ApplicationContext ac = new AnnotationConfigApplicationContext(AopConfig.class);
//
        CdPlayer  cdPlayer = (CdPlayer) ac.getBean("cdPlayer");
//        cdPlayer.playMedia();
        cdPlayer.playMediaAuto();


    }


}
