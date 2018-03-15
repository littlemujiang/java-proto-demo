package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.domain.App;
import com.domain.Service;
import com.mapper_classes.AppMapper2;
import com.mapper_classes.AppMapper;
import com.mapper_classes.ServiceMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class MybatDemo {

    public SqlSession getSqlSession(){

        try {

            String resource = "mybatis-config.xml";

            InputStream inputStream = MybatDemo.class.getClassLoader().getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

//            Reader reader = Resources.getResourceAsReader(resource);
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            SqlSession session = sqlSessionFactory.openSession();

            return session;

        }catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return null;
    }


    private void serviceMgmt(){

        SqlSession session = this.getSqlSession();
        ServiceMapper mapper = session.getMapper(ServiceMapper.class);

        Service service = mapper.getServiceById(333);

        System.out.println(service.getService_name());

    }

    private void appMgmt(){

        MapperHelper mapperHelper = new MapperHelper();
//特殊配置
        Config config = new Config();
//具体支持的参数看后面的文档
        config.setStyle(Style.normal);
//设置配置
        mapperHelper.setConfig(config);
// 注册自己项目中使用的通用Mapper接口，这里没有默认值，必须手动注册
        mapperHelper.registerMapper(AppMapper.class);

        SqlSession session = this.getSqlSession();

        //配置完成后，执行下面的操作
        mapperHelper.processConfiguration(session.getConfiguration());

//        AppMapper2 mapper = session.getMapper(AppMapper2.class);
        AppMapper mapper = session.getMapper(AppMapper.class);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        System.out.println(now);
        System.out.println(now.getTime());

//        mapper.insertApp(9, "第2个", now, now,"9999");

//        mapper.modifyApp("test1111");
//        mapper.modifyAnything("test1111");

        App a = new App(11, "第2个", "22222" , now, now,null);
        mapper.insert(a);

//        List<App> apps = mapper.insert();

        session.commit();
        session.close();

//        System.out.println(apps.size());

    }

    private void appMgmtByClass(){

        SqlSession session = this.getSqlSession();

        AppMapper2 mapper = session.getMapper(AppMapper2.class);


        for(int i=10;i< 50; i++){
            Timestamp now = new Timestamp(System.currentTimeMillis());
            App a = new App();

            a.setApp_id(i);
//            a.setApp_name(String.valueOf(i));
            a.setApp_description("this is app: " + i );
            a.setCreated_at(now);
            a.setModified_at(now);

            mapper.insertAPPByClass(a);
        }

        session.commit();

        List<App> apps = mapper.getApps();

        System.out.println(apps.size());
        System.out.println(apps.get(apps.size()-1).getCreated_at());

    }


    private void getAppService(){

        SqlSession session = this.getSqlSession();
        AppMapper2 unionMapper = session.getMapper(AppMapper2.class);

        List<HashMap> appServices = unionMapper.getAppServices();

        String js = JSON.toJSONString(appServices);


        JSONArray jo = (JSONArray) JSONObject.parse(js);
        JSONArray ja = (JSONArray) JSONObject.parse(jo.toString());

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(jo.toJSONString());

        System.out.println(appServices.get(1).get("service_name"));

    }



    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

        MybatDemo md = new MybatDemo();
//        SqlSession session = abcs.getSqlSession();

//        String statement = "com.domain.serviceMapper.getServiceById";
//        Service service = session.selectOne(statement, 222);

//        abcs.serviceMgmt();

        md.appMgmt();

//        md.getAppService();

//          abcs.appMgmtByClass();


    }


}