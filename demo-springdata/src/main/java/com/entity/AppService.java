package com.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shixk1 on 2017/7/5.
 */
public class AppService implements Serializable {
    public String service_id;
    public String service_statu;
    public Date modified_at;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_statu() {
        return service_statu;
    }

    public void setService_statu(String service_statu) {
        this.service_statu = service_statu;
    }

    public Date getModified_at() {
        return modified_at;
    }

    public void setModified_at(Date modified_at) {
        this.modified_at = modified_at;
    }
}
