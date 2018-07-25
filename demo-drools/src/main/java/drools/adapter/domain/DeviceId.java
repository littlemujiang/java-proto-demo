/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.domain;

import lombok.Data;

/**
 * Simple to Introduction
 * className: MicroClimateDeviceId
 *
 * @author mujiang
 * @version 2018/7/19 下午2:14
 */

@Data
public class DeviceId {

    public String checkType;
    public String type;
    public String subType;
    public String productionDate;
    public String productionSerial;
    public String softVersion;
    public String hardVersion;
    public String reserved1;
    public String reserved2;


    @Override
    public String toString(){
        StringBuilder deviceId = new StringBuilder();

        deviceId.append(checkType);
        deviceId.append(type);
        deviceId.append(subType);
        deviceId.append(productionDate);
        deviceId.append(productionSerial);
        deviceId.append(softVersion);
        deviceId.append(hardVersion);
        deviceId.append(reserved1);
        deviceId.append(reserved2);

        return deviceId.toString();
    }

}