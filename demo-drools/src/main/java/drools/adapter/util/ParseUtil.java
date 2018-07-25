/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package drools.adapter.util;

/**
 * Simple to Introduction
 * className: ParseUtil
 *
 * @author mujiang
 * @version 2018/7/19 下午5:11
 */
public class ParseUtil {

//    public static byte[] Int2Byte(int num){
//        byte[]bytes=new byte[4];
//        bytes[0]=(byte) ((num>>24)&0xff);
//        bytes[1]=(byte) ((num>>16)&0xff);
//        bytes[2]=(byte) ((num>>8)&0xff);
//        bytes[3]=(byte) (num&0xff);
//        return bytes;
//
//    }

    public static int combineData(int ... items){

        int flag = 1;
        StringBuilder s = new StringBuilder();

        for(int item : items){
            if(item < 0){
                flag *= -1;
                item = -item;
            }
            s.append(String.format("%02x",item));
        }
        return  Integer.parseInt(s.toString(),16) * flag;
    }

    public static int combineData4Location(int ... items){
        StringBuilder s = new StringBuilder();
        for(int item : items){
            s.append(String.format("%02x",item));
        }
        return  Integer.parseInt(s.toString(),16);
    }

    public static void main(String[] args) {
        int a  = ParseUtil.combineData4Location(0,1,2,118);
        System.out.println( a);

    }



}