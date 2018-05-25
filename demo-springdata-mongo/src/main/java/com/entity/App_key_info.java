package com.entity;

import java.io.Serializable;

/**
 * Created by shixk1 on 2017/7/4.
 */
public class App_key_info implements Serializable {
    public String app_key, client_id, client_secret;

    public App_key_info(String app_key, String client_id, String client_secret) {
        this.app_key = app_key;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public App_key_info() {
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
