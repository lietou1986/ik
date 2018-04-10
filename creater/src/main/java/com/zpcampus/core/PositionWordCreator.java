package com.zpcampus.core;

import com.zpcampus.util.FileUtils;
import company.wltea.analyzer.core.IKSegmenter;
import company.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Created by len.zhang on 8/21.
 */
public class PositionWordCreator {

    public static void main(String[] args) {
        String inputPath = "E:\\zp\\公司数据\\input\\hotword.txt";
        FileUtils fo = new FileUtils();
        List<String> datas = fo.readFile(inputPath);
        for (String data : datas) {
            IKSegmenter ikSeg = new IKSegmenter(new StringReader(data), false);
            try {
                Lexeme lexeme;
                while (null != (lexeme = ikSeg.next())) {
                    //高频词仅取长度大于1的词
                    if (lexeme.getLexemeText().length() <= 1) {
                        if (lexeme.getLexemeText().equals("与") ||
                                lexeme.getLexemeText().equals("和") ||
                                lexeme.getLexemeText().equals("类")) {
                            continue;
                        }
                        if (lexeme.getLexemeType() == Lexeme.TYPE_CNCHAR) {
                            String term = lexeme.getLexemeText();
                            System.out.println(data + "---" + term);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
