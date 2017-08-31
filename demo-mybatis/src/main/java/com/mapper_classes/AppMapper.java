package com.mapper_classes;

import com.domain.App;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * Created by epcm on 2017/8/25.
 */
public interface AppMapper {

    @Results(id="desmap", value={
            @Result(property = "app_description",column = "description")
    })
    @Select(" select * from app ")
    List<App> getApps();

    @Insert(" insert into app(app_id, app_name, description, created_at, modified_at ) " +
            "values(#{app_id}, #{app_name}, #{description}, #{created_at}, #{modified_at}) ")
    void insertApp(@Param("app_id")int app_id, @Param("app_name")String app_name, @Param("created_at")Timestamp created_at, @Param("modified_at")Timestamp modified_at,@Param("description")String description);


    @Insert(" insert into app(app_id, app_name, description, created_at, modified_at ) " +
            "values(#{app_id}, #{app_name}, #{app_description}, #{created_at}, #{modified_at}) ")
    void insertAPPByClass(App app);


//    @Results(id="union-select")
    @Select("select app.app_name, app.description as app_description, app.created_at as app_created_at, app.modified_at as app_modified_at," +
            "service.service_id, service.service_name, service.description as service_description, service.created_at as service_created_at, service.modified_at as service_modified_at " +
            "from app " +
            "left join service on app.app_name = service.app_id " +
            "where app.app_name = 1111")
    List<HashMap> getAppServices();

    @Select(" select * from app where created_at <= #{cursor} " +
            "order by created_at desc, app_id desc " +
            "limit #{size}")
    List<HashMap> getAppsByPage(@Param("cursor")Timestamp cursor, @Param("size")int pageSize );


}
