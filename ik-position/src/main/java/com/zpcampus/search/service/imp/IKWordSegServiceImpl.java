package com.zpcampus.search.service.imp;

import com.zpcampus.search.model.WordModel;
import com.zpcampus.search.service.WordSegService;
import position.wltea.analyzer.core.IKSegmenter;
import position.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Created by len.zhang on 2016/8/24.
 * ik基本分词
 */
public class IKWordSegServiceImpl implements WordSegService {

    @Override
    public WordModel smartSegWord(String input) {
        input = input.toLowerCase();
        WordModel wordModel = new WordModel();
        wordModel.setWord(input);
        wordModel.setAlterword(input);
        //使用ik智能分词
        IKSegmenter ik = new IKSegmenter(new StringReader(wordModel.getAlterword()), true);
        List<Lexeme> lexemes = wordModel.getLexemes();
        Lexeme lexeme;
        try {
            while (null != (lexeme = ik.next())) {
                lexemes.add(lexeme);
            }
        } catch (IOException e) {
//            LogManager.error("分词器segment流异常", e);
            e.printStackTrace();
        }
        return wordModel;
    }

    /**
     * ik基本分词
     *
     * @param input
     * @return
     */
    public WordModel segWord(String input) {
        input = input.toLowerCase();
        WordModel wordModel = new WordModel();
        wordModel.setWord(input);
        wordModel.setAlterword(input);
        //使用ik最细粒度分词
        IKSegmenter ik = new IKSegmenter(new StringReader(wordModel.getAlterword()), false);
        List<Lexeme> lexemes = wordModel.getLexemes();
        Lexeme lexeme;
        try {
            while (null != (lexeme = ik.next())) {
                lexemes.add(lexeme);
            }
        } catch (IOException e) {
//            LogManager.error("分词器segment流异常", e);
            e.printStackTrace();
        }
        return wordModel;
    }

}
