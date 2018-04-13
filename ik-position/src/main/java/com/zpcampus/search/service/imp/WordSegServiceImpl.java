package com.zpcampus.search.service.imp;

import com.zpcampus.search.model.WordModel;
import position.wltea.analyzer.core.Lexeme;

/**
 * Created by len.zhang on 2016/8/24.
 * 自定义分词实现
 */
public class WordSegServiceImpl extends IKWordSegServiceImpl {

    /**
     * 智能分词
     *
     * @param input
     * @return
     */
    @Override
    public WordModel smartSegWord(String input) {

        WordModel wordModel = super.smartSegWord(input);//IK基本分词

        //分词校准
        WordAdjust wordSegAdjust = new WordAdjust();

        wordModel = wordSegAdjust.adjust(wordModel);

        //分词输出
        String str = null;
        for (Lexeme lexeme1 : wordModel.getLexemes()) {
            if (null == str) {
                str = lexeme1.getLexemeText();
                continue;
            }
            str = str + " " + lexeme1.getLexemeText();
        }
        wordModel.setContent(str);
        return wordModel;
    }

    /**
     * 分词
     *
     * @param input
     * @return
     */
    @Override
    public WordModel segWord(String input) {

        WordModel wordModel = super.segWord(input);//IK基本分词

        //分词校准
        WordAdjust wordSegAdjust = new WordAdjust();

        wordModel = wordSegAdjust.adjust(wordModel);

        //分词输出
        String str = null;
        for (Lexeme lexeme1 : wordModel.getLexemes()) {
            if (null == str) {
                str = lexeme1.getLexemeText();
                continue;
            }
            str = str + " " + lexeme1.getLexemeText();
        }
        wordModel.setContent(str);
        return wordModel;
    }
}
