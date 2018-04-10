package position.wltea.analyzer.test;

import position.wltea.analyzer.core.Lexeme;
import position.wltea.analyzer.core.ZPSegmenter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestIk {

    /**
     * 读取文件，获取文件中的数据
     *
     * @param inputPath 文件所在路径
     */
    public static List<String> readFile(String inputPath) {
        List<String> dataList = new ArrayList<String>();
        String line = "";
        try {
            File file = new File(inputPath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.split("=").length == 2) {
                    dataList.add(line.split("=")[0]);
                }
            }
            br.close();
            isr.close();
        } catch (Exception e) {
            System.out.println(line);
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
    public static void writeFile(String outputPath, List<String> dataList) {

        try {
            File f = new File(outputPath);
            if (!f.exists()) {
                File parentFile = new File(f.getParent());
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
//            FileWriter fw = new FileWriter(outputPath);
            for (String data : dataList) {
                osw.write(data + "\n");
            }
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

//        String keywords = "java工程师,总经理秘书,水质量协调员,基金会计,系统维护工程师,自动化工程师,副总工程师,上海####职位+置顶004";
        String keywords = "ceo助理";
        for (String keyword : keywords.split(",")) {
//            IKSegmenter ik = new IKSegmenter(new StringReader(keyword), false);
//            Lexeme lexeme;
//            while (null != (lexeme = ik.next())) {
//                System.out.println("LexemeText : " + lexeme.getLexemeText());
//                System.out.println("Length : " + lexeme.getLength());
//                System.out.println("BeginPosition : " + lexeme.getBeginPosition());
//                System.out.println("EndPosition : " + lexeme.getEndPosition());
//                System.out.println("LexemeTypeString : " + lexeme.getLexemeTypeString());
//            }
            ZPSegmenter zp = new ZPSegmenter(new StringReader(keyword));
            Lexeme lexeme1;
            while (null != (lexeme1 = zp.next())) {
                System.out.println("LexemeText : " + lexeme1.getLexemeText());
                System.out.println("Length : " + lexeme1.getLength());
                System.out.println("BeginPosition : " + lexeme1.getBeginPosition());
                System.out.println("EndPosition : " + lexeme1.getEndPosition());
                System.out.println("LexemeTypeString : " + lexeme1.getLexemeTypeString());
            }
            System.out.println("-------------------------------------------");
        }


//        List<String> dataList = readFile("E:\\zp\\data\\职位分词\\职位结果.txt");
//        List<String> dataResults = new ArrayList<>();
//        for (String data : dataList) {
//            IKSegmenter ik = new IKSegmenter(new StringReader(data), false);
//            Lexeme lexeme;
//            String result = data + "----";
//            while(null != (lexeme = ik.next())){
//                result += (lexeme.getLexemeText() + "|");
////                System.out.println(lexeme);
//            }
//            dataResults.add(result);
//        }
//        writeFile("E:\\zp\\data\\职位分词\\out_position.txt", dataResults);
    }
}
