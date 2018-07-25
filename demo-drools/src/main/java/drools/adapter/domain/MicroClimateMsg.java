/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.domain;

import lombok.Data;

import java.util.Date;

/**
 * Simple to Introduction
 * className: MicroClimateMsg
 *
 * @author mujiang
 * @version 2018/7/19 下午3:54
 */

@Data
public class MicroClimateMsg extends AbstractDeviceMsg {

    public int status;

    public Date msgDate;
    //经纬度判定
    public int geographic;
    //纬度
    public Double latitude;
    //经度
    public Double longitude;

    //5分钟雨量
    public Double rain5Min;
    //24小时雨量
    public Double rain24Hour;
    //前一天雨量
    public Double rainLastDay;

    //湿度
    public Double humidity;
    //温度
    public Double temperature;
    //照度
    public int illumination;
    //气压
    public int airPressure;
    //风速
    public Double windSpeed;
    //风向
    public Double windDirection;
    //电池电压
    public Double cellVoltage;









}