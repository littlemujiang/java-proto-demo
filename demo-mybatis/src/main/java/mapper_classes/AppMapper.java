package mapper_classes;

import domain.App;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by epcm on 2017/8/25.
 */
public interface AppMapper {

    @Select(" select * from app ")
    List<App> getApps();

    @Insert(" insert into app(app_id, app_name, description, created_at, modified_at ) " +
            "values(#{app_id}, #{app_name}, #{description}, #{created_at}, #{modified_at}) ")
    void insertApp(@Param("app_id")int app_id, @Param("app_name")String app_name, @Param("description")String description, @Param("created_at")Timestamp created_at, @Param("modified_at")Timestamp modified_at);

}
