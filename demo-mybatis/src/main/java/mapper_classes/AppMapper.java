package mapper_classes;

import domain.App;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * Created by epcm on 2017/8/25.
 */
public interface AppMapper {

    @Select(" select * from app ")
    ArrayList<App> getApps();

}
