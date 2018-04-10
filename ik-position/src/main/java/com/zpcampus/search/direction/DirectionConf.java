package com.zpcampus.search.direction;

import com.zpcampus.search.conf.LocalSettings;
import com.zpcampus.search.runtime.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len.zhang on 2016/1/4.
 */
public class DirectionConf {

    static DirectionConf directionConf;

    private static List<String> regionDicionarys;

    private static List<String> theoremDicionarys;

    private static List<String> engineerDicionary;

    private static List<String> conjunctionDicionary;

    private static List<String> polysemyDicionary;

    private static List<String> managerDicionary;

    private static List<String> assistantDicionary;

    private static List<String> numberDicionary;

    private static List<String> worthlessDicionary;

    private static List<String> tagDicionary;

    private static List<String> corporationDicionary;

    public synchronized static DirectionConf Instance() {
        if (directionConf == null) {
            directionConf = new DirectionConf();
        }
        return directionConf;
    }

    private DirectionConf() {
        regionDicionarys = getdicListByFile(LocalSettings.PATH_REGION);
        theoremDicionarys = getdicListByFile(LocalSettings.PATH_THEOREM);
        engineerDicionary = getdicListByFile(LocalSettings.PATH_ENGINEER);
        conjunctionDicionary = getdicListByFile(LocalSettings.PATH_CONJUNCTION);
        polysemyDicionary = getdicListByFile(LocalSettings.PATH_POLYSEMY);
        managerDicionary = getdicListByFile(LocalSettings.PATH_MANAGER);
        assistantDicionary = getdicListByFile(LocalSettings.PATH_ASSISTANT);
        numberDicionary = getdicListByFile(LocalSettings.PATH_NUMBER);
        worthlessDicionary = getdicListByFile(LocalSettings.PATH_WORTHLESS);
        tagDicionary = getdicListByFile(LocalSettings.PATH_TAG);
        corporationDicionary = getdicListByFile(LocalSettings.PATH_CORPORATION);
    }

    private List<String> getdicListByFile(String filePath) {
        List<String> list = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            reader = new BufferedReader(isr);
        } catch (IOException e) {
            LogManager.error("读取词库位置异常IOException", e);
            e.printStackTrace();
        }
        String ss;
        try {
            while ((ss = reader.readLine()) != null) {
                list.add(ss);
            }
        } catch (IOException e) {
            LogManager.error("读文件内容异常IOException", e);
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                LogManager.error("文件流关闭异常IOException", e);
                e.printStackTrace();

            } finally {
                return list;
            }
        }
    }

    public List<String> getRegionDicionarys() {
        return regionDicionarys;
    }

    public List<String> getTheoremDicionarys() {
        return theoremDicionarys;
    }

    public List<String> getEngineerDicionary() {
        return engineerDicionary;
    }

    public List<String> getConjunctionDicionary() {
        return conjunctionDicionary;
    }

    public List<String> getPolysemyDicionary() {
        return polysemyDicionary;
    }

    public List<String> getManagerDicionary() {
        return managerDicionary;
    }

    public List<String> getAssistantDicionary() {
        return assistantDicionary;
    }

    public List<String> getNumberDicionary() {
        return numberDicionary;
    }

    public List<String> getWorthlessDicionary() {
        return worthlessDicionary;
    }

    public List<String> getTagDicionary() {
        return tagDicionary;
    }

    public List<String> getCorporationDicionary() {
        return corporationDicionary;
    }

}
