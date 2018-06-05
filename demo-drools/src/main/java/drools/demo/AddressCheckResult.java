/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.demo;

import lombok.Data;

/**
 * Simple to Introduction
 * className: AddressCheckResult
 *
 * @author mujiang
 * @version 2018/6/5 下午3:07
 */
@Data

public class AddressCheckResult {

    private boolean postCodeResult = false; // true:通过校验；false：未通过校验
    // 省略getter/setter

}