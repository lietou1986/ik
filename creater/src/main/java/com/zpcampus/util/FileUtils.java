package com.zpcampus.util;

import java.io.*;
import java.util.*;

/**
 * Created by len.zhang on 2014/8/6.
 */
public class FileUtils {

    /**
     * 读取文件，获取文件中的数据
     *
     * @param inputPath 文件所在路径
     */
    public List<String> readFile(String inputPath) {
        List<String> dataList = new ArrayList<String>();
        try {
            File file = new File(inputPath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 读取高频词汇文件
     *
     * @param inputPath 文件路径
     * @return 高频词对应的频率
     */
    public Map<String, Integer> readFileMap(String inputPath) {
        Map<String, Integer> dataMap = new HashMap<String, Integer>();
        try {
            File file = new File(inputPath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                dataMap.put(line.split(",")[0], Integer.parseInt(line.split(",")[1]));
            }
            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * 写文件
     *
     * @param outputPath 输出文件目录
     * @param dataList   文件数据
     */
    public void writeFile(String outputPath, List<String> dataList) {

        try {
            File f = new File(outputPath);
            if (!f.exists()) {
                File parentFile = new File(f.getParent());
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(outputPath);
            for (String data : dataList) {
                fw.write(data + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件
     *
     * @param outputPath 输出文件目录
     * @param dataList   文件数据
     */
    public void writeFile(String outputPath, Set<String> dataList) {

        try {
            File f = new File(outputPath);
            if (!f.exists()) {
                File parentFile = new File(f.getParent());
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(outputPath);
            for (String data : dataList) {
                fw.write(data + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件
     *
     * @param outputPath 输出文件目录
     * @param dataMap    文件数据
     */
    public void writeFile(String outputPath, Map<String, Integer> dataMap) {

        try {
            File f = new File(outputPath);
            if (!f.exists()) {
                File parentFile = new File(f.getParent());
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(outputPath);
            Iterator<String> keyIter = dataMap.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                fw.write(key + "," + dataMap.get(key) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
