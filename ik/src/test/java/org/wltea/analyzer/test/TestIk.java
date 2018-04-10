package org.wltea.analyzer.test;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class TestIk {
    public static void main(String[] args) throws IOException {
        IKSegmenter ik = new IKSegmenter(new StringReader("五万"), false);
        Lexeme lexeme;
        while (null != (lexeme = ik.next())) {
//            System.out.println(lexeme.getBeginPosition() + "---" + lexeme.getBegin());
            System.out.println(lexeme);
        }
    }
}
