package com.mapper_classes;

import com.domain.Service;
import org.apache.ibatis.annotations.Select;

/**
 * Created by epcm on 2017/8/25.
 */
public interface ServiceMapper {


    @Select(" select * from service where service_id=#{service_id}")
    Service getServiceById(int service_id);

}
