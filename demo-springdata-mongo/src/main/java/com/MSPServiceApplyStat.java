package com;

import com.config.MongodbConfig;
import com.entity.App;
import com.entity.ApplyService;
import com.entity.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mujiang on 2017/10/23.
 */
public class MSPServiceApplyStat {


    MongoTemplate mongoTemplate = null;

    private MongoTemplate mongoInit(){

        try {
            if(mongoTemplate == null ){
                MongodbConfig mongodbConfig = new MongodbConfig();
                this.mongoTemplate = mongodbConfig.mongoTemplate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongoTemplate;
    }


    public void getAppliedService(){

        mongoInit();

        String queryAppString = "{deleted:false}";
        BasicQuery queryApp = new BasicQuery(queryAppString);
        List<App> appList =  mongoTemplate.find(queryApp, App.class);
        HashMap<String,String> appIDtoName = new HashMap<String, String>();
        for(App app : appList){
            appIDtoName.put(app.getId(), app.getName());
        }


        String queryServiceString = "{deleted:false}";
        BasicQuery queryService = new BasicQuery(queryServiceString);
        List<Service> serviceList =  mongoTemplate.find(queryService, Service.class, "service");
        HashMap<String,String> serviceIDtoName = new HashMap<String, String>();
        for(Service service : serviceList){
            serviceIDtoName.put(service.getId(), service.getService_name());
        }


//        IdentityHashMap<String,Object> appService = new IdentityHashMap();
        HashMap<String, ArrayList<String> > appService = new HashMap();

        for(App app : appList){

            String appName = app.getName();

            List<ApplyService> applyServices = app.getApplyServices();

            if(applyServices == null)
                continue;

            for(ApplyService applyService : applyServices){
                if(applyService.getApplyStatus().equals("empowered")){

                    String serviceName = serviceIDtoName.get(applyService.getServiceId());

                    if(serviceName == null)
                        continue;
                    //初始化结果Map中的 array list
                    if(appService.get(serviceName) == null ){
                       appService.put(serviceName , new ArrayList<String>() );
                    }
                    //加入服务的 owner appname
                    if(!appService.get(serviceName).contains(appName)){
                        appService.get(serviceName).add(appName);
                    }

                    String applyAppName = appIDtoName.get(applyService.getApplyerAppId());
                    if(applyAppName !=null){
                        appService.get(serviceName).add(applyAppName);
                    }
                }
            }
        }

        System.out.println("==========="+appService.size());

        System.out.println("~~~~~~~~~");
        for(String serviceName : appService.keySet()){

            if(serviceName == null)
                continue;

            System.out.print(serviceName+"\t");
            ArrayList<String> apps = appService.get(serviceName);
            if(apps != null){
                for(String app : apps){
                    System.out.print(app+"\t");
                }
            }
            System.out.print( "\r\n");
        }
        System.out.println("~~~~~~~~~");

//        System.out.println("==========="+appService);

    }

    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

        new MSPServiceApplyStat().getAppliedService();
    }
}
