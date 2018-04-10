package company.wltea.analyzer.test;

import company.wltea.analyzer.core.IKSegmenter;
import company.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class TestIk {
    public static void main(String[] args) throws IOException {
        IKSegmenter ik = new IKSegmenter(new StringReader("北京市政客"), false);
        Lexeme lexeme;
        while (null != (lexeme = ik.next())) {
            System.out.println(lexeme);
        }
    }
}
