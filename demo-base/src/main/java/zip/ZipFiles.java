/*
 * Copyright (C) 2019 org.citic.iiot, Inc. All Rights Reserved.
 */


package zip;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import sun.misc.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Simple to Introduction
 * className: ZipFiles
 *
 * @author mujiang
 * @version 2019/2/21 9:01 AM
 */
public class ZipFiles {

    private static final int  BUFFER_SIZE = 2 * 1024;

    public void zipFiles(){


        ZipOutputStream zos = null ;
        InputStream fixInputStream = null;

        try {
            FileOutputStream zipOut = new FileOutputStream(new File("/Users/mujiang/Documents/temp/mytest01.zip"));
            zos = new ZipOutputStream(zipOut);

            // 固定文件
            ClassPathResource resource = new ClassPathResource("mqtt-server-config.yml");
            fixInputStream = resource.getInputStream();
            File fixFile = new File("");


            //动态文件
            JSONObject congfig = new JSONObject();
            congfig.put("eid","aaaaa");
            congfig.put("ts","ssssss");

            InputStream changeInputStream = new ByteArrayInputStream(congfig.toString().getBytes());
//            InputStream changeInputStream = IOUtils.toInputStream(congfig.toString(), StandardCharsets.UTF_8.name());


            HashMap<String, InputStream> srcFiles = new HashMap();
            srcFiles.put("111.yml", fixInputStream);
            srcFiles.put("222.txt", changeInputStream);



            for (String fileName : srcFiles.keySet()) {
                InputStream in = srcFiles.get(fileName);
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(fileName));
                int len;
//                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                    InputStream temp = new ByteArrayInputStream(buf);

                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        zos.toString();

    }


    public void readZip(){

        ZipOutputStream zos = null ;

        InputStream fixInputStream = null;
        ZipInputStream zipInputStream = new ZipInputStream(fixInputStream);

        ZipEntry zipEntry=null;
        try {
            while((zipEntry=zipInputStream.getNextEntry())!=null){
//                String fileName_zip=zipEntry.getName();
                zos.putNextEntry(zipEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
       new ZipFiles().zipFiles();
    }



}