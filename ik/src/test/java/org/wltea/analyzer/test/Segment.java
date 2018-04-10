package org.wltea.analyzer.test;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len.zhang on 11/2.
 */
public class Segment {


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

    public List<String> readAllFile(String inputPath) {
        List<String> dataList = new ArrayList<String>();
        try {
            File[] files = new File(inputPath).listFiles();
            for (File file : files) {
                if (!file.getName().startsWith("out_")) {
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
        } catch (Exception e) {
            e.printStackTrace();
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
                fw.write(data + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> ikSengment(List<String> datas) {
        List<String> segments = new ArrayList<String>();
        for (String data : datas) {
            IKSegmenter ik = new IKSegmenter(new StringReader(data), true);
            Lexeme lexeme;
            try {
                String segment = "";
                while (null != (lexeme = ik.next())) {
                    segment += (lexeme.getLexemeText() + "|");
                }
                if (segment.endsWith("|")) {
                    segment = segment.substring(0, segment.length() - 1);
                }
                if (!data.equals(segment))
                    segments.add(data + "------" + segment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return segments;
    }


    public List<String> fmmSegment(List<String> datas, List<String> dictionary) {
        List<String> result = new ArrayList<String>();
        for (String data : datas) {
            String response = "";
            int start = 0;
            int end = data.length();
            while (start < end) {
                String s = data.substring(start, end);
                if (s.length() == 1 || dictionary.contains(s)) {
                    response += (s + "|");
                    start = end;
                    end = data.length();
                } else {
                    end--;
                }
            }
            if (response.endsWith("|")) {
                response = response.substring(0, response.length() - 1);
            }
            if (!data.equals(response))
                result.add(data + "------" + response);
        }

        return result;
    }

    public List<String> bmmSegment(List<String> datas, List<String> dictionary) {
        List<String> result = new ArrayList<String>();
        for (String data : datas) {
            String response = "";
            int start = 0;
            int end = data.length();
            while (start < end) {
                String s = data.substring(start, end);
                if (s.length() == 1 || dictionary.contains(s)) {
                    response = s + "|" + response;
                    end = start;
                    start = 0;
                } else {
                    start++;
                }
            }
            if (response.endsWith("|")) {
                response = response.substring(0, response.length() - 1);
            }
            if (!data.equals(response))
                result.add(data + "------" + response);
        }
        return result;
    }


    public static void main(String[] args) {

        String inputPath = "E:\\zp\\data\\第二阶段";
        String outPath = "E:\\zp\\data\\第二阶段\\";
        String dicPath = "D:\\allPro_idea\\wordstock\\segment-ik\\src\\main\\resources\\main2012.dic";
        Segment segment = new Segment();
        segment.writeFile(outPath + "out_ik.txt", segment.ikSengment(segment.readAllFile(inputPath)));

        List<String> dictionary = segment.readFile(dicPath);

        segment.writeFile(outPath + "out_fmm.txt", segment.fmmSegment(segment.readAllFile(inputPath), dictionary));
        segment.writeFile(outPath + "out_bmm.txt", segment.bmmSegment(segment.readAllFile(inputPath), dictionary));
    }
}
