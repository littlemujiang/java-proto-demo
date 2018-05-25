package com.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by shixk1 on 2017/7/4.
 */
public class App implements Serializable {
    @Id
    public String id;

    public String name;
    public String owner;
    public String modifided_by;
    public App_key_info app_key_info;
    public String description;
    public Boolean deleted;
    public List<Developer> developers;
    public Date created_at, modified_at;
    public List<AppService> appServices; //全部服务
    public List<ApplyService> applyServices; //全部申请的服务


    public App(String name, Date created_at, Date modified_at, String description, String owner) {
        this.name = name;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.description = description;
        this.owner = owner;
        this.deleted = false;
        this.modifided_by = owner;
//        this.applyServices = applyServices;
    }

    public App() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModifided_by() {
        return modifided_by;
    }

    public void setModifided_by(String modifided_by) {
        this.modifided_by = modifided_by;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getModified_at() {
        return modified_at;
    }

    public void setModified_at(Date modified_at) {
        this.modified_at = modified_at;
    }

    public App_key_info getApp_key_info() {
        return app_key_info;
    }

    public void setApp_key_info(App_key_info app_key_info) {
        this.app_key_info = app_key_info;
    }


    public List<AppService> getAppServices() {
        return appServices;
    }

    public void setAppServices(List<AppService> appServices) {
        this.appServices = appServices;
    }

    public List<ApplyService> getApplyServices() {
        return applyServices;
    }

    public void setApplyServices(List<ApplyService> applyServices) {
        this.applyServices = applyServices;
    }
}
