package org.wltea.analyzer;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class TestIk {
    public static void main(String[] args) throws IOException {
        IKSegmenter ik = new IKSegmenter(new StringReader("ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由"), false);
        Lexeme lexeme;
        while (null != (lexeme = ik.next())) {
            System.out.println(lexeme);
        }
    }
}
