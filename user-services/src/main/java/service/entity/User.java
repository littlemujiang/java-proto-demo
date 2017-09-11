package service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

/**
 * Created by epcm on 2017/7/12.
 */

//@Document
public class User implements Serializable {

    @Id
    private String _id;

    @Indexed
    private String user_id;

    @Indexed
    private String username;

    private String password;

    private String email;
    private boolean isEmailVeryfied = false;

    private String phoneNumber;
    private boolean isPhoneNumberVerified = false;

    private Long created_at;
    private Long modified_at;

    private String appkey;

    private String emailVerifyCode;
    private long emailVerifyTime;
    private String passwordVerifyCode;
    private long passwordVerifyTime;

    private boolean isValid = false;
    private String wechatNumber;


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVeryfied() {
        return isEmailVeryfied;
    }

    public void setEmailVeryfied(boolean emailVeryfied) {
        isEmailVeryfied = emailVeryfied;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPhoneNumberVerified() {
        return isPhoneNumberVerified;
    }

    public void setPhoneNumberVerified(boolean phoneNumberVerified) {
        isPhoneNumberVerified = phoneNumberVerified;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Long getModified_at() {
        return modified_at;
    }

    public void setModified_at(Long modified_at) {
        this.modified_at = modified_at;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getEmailVerifyCode() {
        return emailVerifyCode;
    }

    public void setEmailVerifyCode(String emailVerifyCode) {
        this.emailVerifyCode = emailVerifyCode;
    }

    public long getEmailVerifyTime() {
        return emailVerifyTime;
    }

    public void setEmailVerifyTime(long emailVerifyTime) {
        this.emailVerifyTime = emailVerifyTime;
    }

    public String getPasswordVerifyCode() {
        return passwordVerifyCode;
    }

    public void setPasswordVerifyCode(String passwordVerifyCode) {
        this.passwordVerifyCode = passwordVerifyCode;
    }

    public long getPasswordVerifyTime() {
        return passwordVerifyTime;
    }

    public void setPasswordVerifyTime(long passwordVerifyTime) {
        this.passwordVerifyTime = passwordVerifyTime;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }
}
