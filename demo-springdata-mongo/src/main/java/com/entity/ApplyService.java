package com.entity;

import java.util.Date;

/**
 * Created by mujiang on 2017/10/23.
 */
public class ApplyService {

    public String applyerAppId;
    public String applyerAppName;
    public String applyer;

    public String serviceId;
    public String serviceName;
    public String applyStatus;
    public Date applyTime;
    public Date auditTime;


    public String getApplyerAppId() {
        return applyerAppId;
    }

    public void setApplyerAppId(String applyerAppId) {
        this.applyerAppId = applyerAppId;
    }

    public String getApplyerAppName() {
        return applyerAppName;
    }

    public void setApplyerAppName(String applyerAppName) {
        this.applyerAppName = applyerAppName;
    }

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
}
