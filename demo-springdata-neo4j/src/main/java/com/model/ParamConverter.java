/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Simple to Introduction
 * className: ParamConverter
 *
 * @author mujiang
 * @version 2018/4/8 下午5:38
 */
public class ParamConverter implements CompositeAttributeConverter<Param> {


    @Override
    public Map<String, ?> toGraphProperties(Param json) {
        Map<String, String> properties = new HashMap();
        if (json != null)  {
            try {
//                Iterator keys = json.keys();
//                while(keys.hasNext()){
//                    String key = (String) keys.next();
//                    properties.put(key, (String) json.get(key));
//                }

                for(String key : json.keySet()){
                    properties.put(key, (String) json.get(key));
                }
                return properties;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public Param toEntityAttribute(Map<String, ?> map) {

            try {
//                JSONObject obj = new JSONObject();
                Param obj = new Param();
                if(map != null){
                    for(String key : map.keySet()){
                        if(key.startsWith("_")){
                            obj.put(key, map.get(key));
                        }
                    }
                }
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

}