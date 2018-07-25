/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.domain;

import lombok.Data;

/**
 * Simple to Introduction
 * className: AbstractDeviceMsg
 *
 * @author mujiang
 * @version 2018/7/24 下午6:23
 */

@Data
public class AbstractDeviceMsg {

    public String deviceId;
    public String MAC;

}