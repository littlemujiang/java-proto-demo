/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Simple to Introduction
 * className: BigDecimalToDoubleConverter
 *
 * @author mujiang
 * @version 2018/7/23 上午11:14
 */

@Component
public class DotToMarkConverter implements Converter<String, String> {

    @Override
    public String  convert(String source) {
        if(source != null && source.contains("\\.")){
            source.replaceAll("\\.","!");
        }
        return source;
    }
}