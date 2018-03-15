package com.mapper_classes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.domain.App;
import com.domain.FunObject;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by epcm on 2017/8/25.
 */
public interface AppMapper2 {

    @Results(id="desmap", value={
            @Result(property = "app_description",column = "description")
    })
    @Select(" select * from app ")
    List<App> getApps();

    @Insert(" insert into app(app_id, app_name, description, created_at, modified_at ) " +
            "values(#{app_id}, #{app_name}, #{description}, #{created_at}, #{modified_at}) ")
    void insertApp(@Param("app_id") int app_id, @Param("app_name") String app_name, @Param("created_at") Timestamp created_at, @Param("modified_at") Timestamp modified_at, @Param("description") String description);


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
    List<HashMap> getAppsByPage(@Param("cursor") Timestamp cursor, @Param("size") int pageSize);

    @Update("UPDATE app SET description=#{description} WHERE app_name='1111';")
    void modifyApp(@Param("description") String description);

    @UpdateProvider(type = ModifySqlBuilder.class, method = "buildModifyAnythingByObject")
    void modifyAnything(@Param("domain") Object domain);

    class ModifySqlBuilder{
        public String buildModifyAnythingByObject(@Param("domain") final Object domain){
            new SQL(){};
            return "";
        }
    }

    @InsertProvider(type = InsertSqlBuilder.class, method = "buildInsertAnyObject")
    void insertAnything(@Param("domain") Object domain);

    @InsertProvider(type = InsertSqlBuilder.class, method = "buildInsertAnyJson")
    void insertAnyJson(@Param("domain") JSON domain);

    class InsertSqlBuilder {

        public String buildInsertAnyObject(@Param("domain") FunObject domain) {
            SQL sql = new SQL().INSERT_INTO("app");
            Field[] fields = domain.getData().getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    System.out.println(field.getName());
                    System.out.println(field.getType());
                    Object value = field.get(domain.getData());
                    if (value != null) {
                        sql.VALUES(field.getName(), "'" + value.toString() + "'");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return sql.toString();
        }

        public String buildInsertAnyJson(@Param("domain") JSONObject domain) {
            SQL sql = new SQL().INSERT_INTO("app");
            Set<String> keySet = domain.keySet();
            for (String key : keySet) {

            }
            return sql.toString();
        }
    }
}
