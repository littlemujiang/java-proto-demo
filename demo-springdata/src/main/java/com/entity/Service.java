package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by simon on 2017/6/7.
 */
@Document(collection = "service")
public class Service implements Serializable {

    @Id
    public String id;// service_key
    public String app_id;

    public String service_name;
    public Date created_at, modified_at;
    public String code_url;
    public String description;
    public Boolean deleted;
    public String cn_caption;
    public String owner;



    public String evnstrname;


    public String getEvnstrname() {
        return evnstrname;
    }
    public void setEvnstrname(String evnstrname) {
        this.evnstrname = evnstrname;
    }


    Map<String,Set<String>> iplist;
    public ArrayList <String> tagnames;

//	@DBRef
//	public List<ServiceTag> tags;

    public Service(String service_name, Date date, String description,
                    String cn_caption, String owner,
                   String app_id,ArrayList<String> tagnames,Map<String,Set<String>> iplist) {
        this.service_name = service_name;
        this.created_at = date;
        this.modified_at = date;
        this.description = description;
        this.deleted = false;
        this.owner = owner;
//		this.tags = tags;
        this.app_id = app_id;
        this.cn_caption = cn_caption;
        this.tagnames=tagnames;
        this.iplist = iplist;
    }

    public ArrayList<String> getTagnames() {
        return tagnames;
    }


    public void setTagnames(ArrayList<String> tagnames) {
        this.tagnames = tagnames;
    }

    public Map<String, Set<String>> getIplist() {
        return iplist;
    }


    public void setIplist(Map<String, Set<String>> iplist) {
        this.iplist = iplist;
    }

    public Service() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
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

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

//	public List<ServiceTag> getTags() {
//		return tags;
//	}
//
//	public void setTags(List<ServiceTag> tags) {
//		this.tags = tags;
//	}

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getCn_caption() {
        return cn_caption;
    }

    public void setCn_caption(String cn_caption) {
        this.cn_caption = cn_caption;
    }

}
