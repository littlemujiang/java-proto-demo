package drools;/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


import drools.adapter.rules.AIOData;
import drools.adapter.rules.AIODataParsed;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Simple to Introduction
 * className: AIOData
 *
 * @author mujiang
 * @version 2018/7/19 上午10:08
 */

public class DataParser {

    private static final String RULES_PATH = "rules/";


    private KieSession getKieSession(String RULES_PATH){

        KieSession kieSession = null;

        try {
            //生成服务
            KieServices kieServices = KieServices.Factory.get();

            //加载规则文件
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            for (Resource file : resources) {
                kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
            }

            //加载module
            final KieRepository kieRepository = kieServices.getRepository();
            kieRepository.addKieModule(new KieModule() {
                @Override
                public ReleaseId getReleaseId() {
                    return kieRepository.getDefaultReleaseId();
                }
            });

            //build kie
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();

            //生成 kie container
            KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());

            //生成 kie session
            kieSession =  kieContainer.newKieSession();


        }catch (Exception e){
            System.out.println(e.toString());
        }

        return kieSession;

    }

    public void dataParse(){

            KieSession kieSession =  getKieSession("rules/aio/");

            AIOData aioData = new AIOData();
            aioData.setHumidity(50);
            aioData.setTemperature(30);

            AIODataParsed aioDataParsed = new AIODataParsed();

            kieSession.insert(aioData);
            kieSession.insert(aioDataParsed);

            int ruleFiredCount = kieSession.fireAllRules();

            System.out.println("触发了" + ruleFiredCount + "条规则");

            System.out.println("湿度质量： " + aioDataParsed.getHumidity());
            System.out.println("温度质量： " + aioDataParsed.getTemperature());
    }

        public static void main(String[] args) {

            new DataParser().dataParse();

        }
}