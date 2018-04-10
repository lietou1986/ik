package com.zpcampus.search.conf;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by len.zhang on 2016/1/19.
 */
public class LocalSettings {
    public static String PATH_REGION;
    public static String PATH_THEOREM;
    public static String PATH_ENGINEER;
    public static String PATH_CONJUNCTION;
    public static String PATH_POLYSEMY;
    public static String PATH_MANAGER;
    public static String PATH_ASSISTANT;
    public static String PATH_NUMBER;
    public static String PATH_WORTHLESS;
    public static String PATH_TAG;
    public static String PATH_CORPORATION;

    public static String COMPANY_SOLR;


    static {
        InputStream is = LocalSettings.class.getClassLoader().getResourceAsStream("config.properties");
        Properties props = new Properties();
        try {
            props.load(is);
            PATH_REGION = props.getProperty("dic.name.region").trim();
            PATH_THEOREM = props.getProperty("dic.name.theorem").trim();
            PATH_ENGINEER = props.getProperty("dic.name.engineer").trim();
            PATH_CONJUNCTION = props.getProperty("dic.name.conjunction").trim();
            PATH_POLYSEMY = props.getProperty("dic.name.polysemy").trim();
            PATH_MANAGER = props.getProperty("dic.name.manager").trim();
            PATH_ASSISTANT = props.getProperty("dic.name.assistant").trim();
            PATH_NUMBER = props.getProperty("dic.name.number").trim();
            PATH_WORTHLESS = props.getProperty("dic.name.worthless").trim();
            PATH_TAG = props.getProperty("dic.name.tag").trim();
            PATH_CORPORATION = props.getProperty("dic.name.corporation").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (props.containsKey("company.solr")) {
            COMPANY_SOLR = props.getProperty("company.solr");
        }
    }
}
