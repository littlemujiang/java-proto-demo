package com.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shixk1 on 2017/7/4.
 */
public class Developer implements Serializable {
    public String role, user_name, display_name;
    public Date created_at;

    public Developer(String role, String user_name, String display_name, Date created_at) {
        this.role = role;
        this.user_name = user_name;
        this.display_name = display_name;
        this.created_at = created_at;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
