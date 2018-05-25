/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package object;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Simple to Introduction
 * className: DynamicObject
 *
 * @author mujiang
 * @version 2018/4/8 下午6:53
 */
public class DynamicObject {

    public static void main(String[] args){


        try {

//            JSONObject json = new JSONObject();
//            json.put("latitude", "经度");
//            json.put("longitude", "纬度");

            DynamicObject dObj = new DynamicObject();
            Object student1 = null;
            Map<String, Object> fieldMap = new HashMap<String, Object>();// 属性-取值map

            fieldMap.put("name", "xiao ming");
            fieldMap.put("age", 27);

            student1 = dObj.addField("Student", fieldMap);// 创建一个名称为Student的类
            Class c = Class.forName("Student");

            Object s1 = c.newInstance();// 创建Student类的对象
            Object s2 = c.newInstance();

            dObj.setFieldValue(s1, "name", " xiao ming ");// 创建对象s1赋值
            dObj.setFieldValue(s2, "name", "xiao zhang");

            fieldMap.clear();
            List<Object> students = new ArrayList<Object>();
            students.add(s1);
            students.add(s2);

            fieldMap.put("students", students);


        }catch (Exception e){
          e.printStackTrace();
        }



    }

    public Object addField(String className, Map<String, Object> fieldMap)

            throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {

        ClassPool pool = ClassPool.getDefault();// 获取javassist类池
        CtClass ctClass = pool.makeClass(className, pool.get(Object.class.getName()));// 创建javassist类
        // 为创建的类ctClass添加属性
        Iterator it = fieldMap.entrySet().iterator();

        while (it.hasNext()) { // 遍历所有的属性

            Map.Entry entry = (Map.Entry) it.next();
            String fieldName = (String) entry.getKey();
            Object fieldValue = entry.getValue();

            // 增加属性，这里仅仅是增加属性字段
            String fieldType = fieldValue.getClass().getName();
            CtField ctField = new CtField(pool.get(fieldType), fieldName, ctClass);
            ctField.setModifiers(Modifier.PUBLIC);
            ctClass.addField(ctField);
        }

        Class c = ctClass.toClass();// 为创建的javassist类转换为java类
        Object newObject = c.newInstance();// 为创建java对象
        // 为创建的类newObject属性赋值

        it = fieldMap.entrySet().iterator();
        while (it.hasNext()) { // 遍历所有的属性

            Map.Entry entry = (Map.Entry) it.next();
            String fieldName = (String) entry.getKey();
            Object fieldValue = entry.getValue();

            // 为属性赋值
            this.setFieldValue(newObject, fieldName, fieldValue);
        }
        return newObject;
    }

    public Object setFieldValue(Object dObject, String fieldName, Object val) {

        Object result = null;

        try {
            Field fu = dObject.getClass().getDeclaredField(fieldName); // 获取对象的属性域
            try {
                fu.setAccessible(true); // 设置对象属性域的访问属性
                fu.set(dObject, val); // 设置对象属性域的属性值
                result = fu.get(dObject); // 获取对象属性域的属性值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return result;
    }


}