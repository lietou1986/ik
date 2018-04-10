package com.zpcampus.position;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len.zhang on 10/14.
 */
public class KeyWordCreator {

    public List<String> readAllFile(String inputPath) throws IOException {
        File[] files = new File(inputPath).listFiles();
        List<String> dataList = new ArrayList<String>();
        for (File file : files) {
            if (file.getName().startsWith("zp_keywords_")) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    dataList.add(line);
                }
                br.close();
                isr.close();
            }
        }
        return dataList;
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
                fw.write(data.split("\t")[1] + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inputPath = "E:\\zp\\data\\keywords";
        String outFile = "E:\\zp\\data\\keywords\\allKeyword.txt";
        KeyWordCreator kc = new KeyWordCreator();
        try {
            List<String> dataList = kc.readAllFile(inputPath);
            kc.writeFile(outFile, dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
