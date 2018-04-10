package com.zpcampus.search.service.imp;

import com.zpcampus.search.direction.DirectionConf;
import com.zpcampus.search.model.WordModel;
import position.wltea.analyzer.core.Lexeme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by len.zhang on 2016/8/29.
 * 分词结果校准器
 */
public class WordAdjust {

    /**
     * 分词特殊处理
     *
     * @param wordModel
     * @return
     */
    public WordModel adjust(WordModel wordModel) {

        DirectionConf directionConf = DirectionConf.Instance();

        String s = wordModel.getAlterword();

        List<Lexeme> wordList = wordModel.getLexemes();
        //地区词处理
        wordList = regionWord(wordList, directionConf.getRegionDicionarys());

        //定理词处理
        wordList = regionWord(wordList, directionConf.getTheoremDicionarys());

        //完全双交叉处理
        Lexeme lexeme = null;
        wordList = dualcrossing(wordList, lexeme);

        //双交叉处理
        wordList = allintersect(wordList, lexeme);

        //加词   副总工程师 副总裁  副总经理
        wordList = addwrod(wordList, directionConf.getEngineerDicionary(), s);

        //对多义词进行处理
        wordList = accession(wordList, directionConf.getPolysemyDicionary(), s);

        //对秘书、助理进行特殊处理
        wordList = alterwrod(wordList, directionConf.getAssistantDicionary(),
                directionConf.getManagerDicionary(), s);

        //单字处理（去除）
        wordList = singlewrod(wordList);

        //删除连词
        wordList = conjunction(wordList, directionConf.getConjunctionDicionary());

        wordModel.setLexemes(wordList);

        return wordModel;
    }

    /**
     * 地区词处理
     * 功能：对地区词、定理词进行处理
     *
     * @param wordList
     * @return
     */
    private List<Lexeme> regionWord(List<Lexeme> wordList, List<String> regionDic) {
        //判断wordList是否存在地区词、定理词   如果不存在地区词  直接返回 不参加下面的运算
        if (decision(wordList, regionDic)) {
            return wordList;
        }
        //要删除词的list
        List<Lexeme> deleteList = new ArrayList<Lexeme>();
        for (Lexeme lexeme1 : wordList) {
            String s1 = lexeme1.getLexemeText();
            int stra1 = lexeme1.getBeginPosition();
            int end1 = lexeme1.getEndPosition();
            if (regionDic.contains(s1)) {
                for (Lexeme lexeme2 : wordList) {
                    String s2 = lexeme2.getLexemeText();
                    int stra2 = lexeme2.getBeginPosition();
                    int end2 = lexeme2.getEndPosition();
                    if ((!s1.equals(s2)) && ((stra1 <= stra2 && stra2 <= end1) || (stra1 < end2 && end2 <= end1))) {
                        deleteList.add(lexeme2);
                    }
                }
            }
        }
        for (Lexeme lexeme : deleteList) {
            wordList.remove(lexeme);
        }
        return wordList;
    }

    /**
     * 功能：判断是否存在定理词或地区词
     *
     * @param wordList  分词list
     * @param regionDic 定理词或地区词
     * @return
     */
    private Boolean decision(List<Lexeme> wordList, List<String> regionDic) {
        for (Lexeme lexeme : wordList) {
            if (regionDic.contains(lexeme.getLexemeText())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 功能：通过递归去掉完全双交叉
     *
     * @param wordList
     * @param lexeme
     * @return
     */
    private List<Lexeme> dualcrossing(List<Lexeme> wordList, Lexeme lexeme) {
        Boolean temp = false;
        List<Lexeme> newWordList = new ArrayList<Lexeme>();
        if (wordList.contains(lexeme)) {
            wordList.remove(lexeme);
        }
        for (Lexeme lexeme1 : wordList) {
            String s1 = lexeme1.getLexemeText();
            int stra1 = lexeme1.getBeginPosition();
            int end1 = lexeme1.getEndPosition();
            for (Lexeme lexeme2 : wordList) {
                String s2 = lexeme2.getLexemeText();
                int stra2 = lexeme2.getBeginPosition();
                int end2 = lexeme2.getEndPosition();
                if ((!s1.equals(s2)) && (stra2 < stra1 && stra1 < end2)) {
                    for (Lexeme lexeme3 : wordList) {
                        String s3 = lexeme3.getLexemeText();
                        int stra3 = lexeme3.getBeginPosition();
                        int end3 = lexeme3.getEndPosition();
                        if (!s1.equals(s3) && !s2.equals(s3) && (stra3 < end1 && end1 < end3)) {
                            newWordList = dualcrossing(wordList, lexeme1);
                            temp = true;
                            break;
                        }
                    }
                    if (temp) {
                        break;
                    }
                }
            }
            if (temp) {
                break;
            }
        }
        if (temp) {
            return newWordList;
        } else {
            return wordList;
        }
    }

    /**
     * 功能：通过递归去掉双交叉
     *
     * @param wordList
     * @param lexeme
     * @return
     */
    private List<Lexeme> allintersect(List<Lexeme> wordList, Lexeme lexeme) {
        Boolean temp = false;
        List<Lexeme> newWordList = new ArrayList<Lexeme>();
        if (wordList.contains(lexeme)) {
            wordList.remove(lexeme);
        }
        for (Lexeme lexeme1 : wordList) {
            String s1 = lexeme1.getLexemeText();
            int stra1 = lexeme1.getBeginPosition();
            int end1 = lexeme1.getEndPosition();
            for (Lexeme lexeme2 : wordList) {
                String s2 = lexeme2.getLexemeText();
                int stra2 = lexeme2.getBeginPosition();
                int end2 = lexeme2.getEndPosition();
                if ((!s1.equals(s2)) && (stra2 <= stra1 && stra1 < end2)) {
                    for (Lexeme lexeme3 : wordList) {
                        String s3 = lexeme3.getLexemeText();
                        int stra3 = lexeme3.getBeginPosition();
                        int end3 = lexeme3.getEndPosition();
                        if (!s1.equals(s3) && !s2.equals(s3) && (stra3 < end1 && end1 <= end3)) {
                            if (!(stra1 == stra2 && end1 == end3)) {
                                if (!(s2.contains(s1) | s3.contains(s1))) {
                                    newWordList = allintersect(wordList, lexeme1);
                                    temp = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (temp) {
                        break;
                    }
                }
            }
            if (temp) {
                break;
            }
        }
        if (temp) {
            return newWordList;
        } else {
            return wordList;
        }
    }

    /**
     * 单字词处理  删除被包含的单字词
     *
     * @param wordList
     * @return
     */
    private List<Lexeme> singlewrod(List<Lexeme> wordList) {
        List<Lexeme> deleteWord = new ArrayList<>();
        for (Lexeme lexeme1 : wordList) {
            String s1 = lexeme1.getLexemeText();
            int stra1 = lexeme1.getBeginPosition();
            int end1 = lexeme1.getEndPosition();
            if (s1.length() == 1) {
                for (Lexeme lexeme2 : wordList) {
                    String s2 = lexeme2.getLexemeText();
                    int stra2 = lexeme2.getBeginPosition();
                    int end2 = lexeme2.getEndPosition();
                    if (!s1.equals(s2) && (stra2 <= stra1 && end1 <= end2)) {
                        deleteWord.add(lexeme1);
                    }
                }
            }
        }
        for (Lexeme lexeme : deleteWord) {
            wordList.remove(lexeme);
        }
        return wordList;
    }

    /**
     * 特殊词处理（副总工程师 副总裁  副总经理）
     *
     * @param wordList
     * @param engineerDic
     * @param s
     * @return
     */
    private List<Lexeme> addwrod(List<Lexeme> wordList, List<String> engineerDic, String s) {
        for (String str : engineerDic) {
            if (s.contains(str)) {
                if (engineerDic.indexOf(str) == 0) {
                    int begin = s.indexOf(str.substring(1, 3));
                    Lexeme lexeme = new Lexeme(0, begin, begin + 2, 4);
                    lexeme.setLexemeText(str.substring(1, 3));
                    wordList.add(lexeme);
                } else {
                    int begin = s.indexOf(str);
                    Lexeme lexeme = new Lexeme(0, begin, begin + 2, 4);
                    lexeme.setLexemeText(str.substring(0, 2));
                    wordList.add(lexeme);
                }
            }
        }
        return wordList;
    }

    /**
     * 多义词处理
     *
     * @param wordList
     * @param polysemyDic
     * @param s
     * @return
     */
    private List<Lexeme> accession(List<Lexeme> wordList, List<String> polysemyDic, String s) {
        for (String str : polysemyDic) {
            if (s.contains(str)) {
                int begin = s.indexOf(str);
                Lexeme lexeme = new Lexeme(0, begin, begin + 2, 4);
                lexeme.setLexemeText(str.replace(str.substring(1, 2), ""));
                wordList.add(lexeme);
            }
        }
        return wordList;
    }

    /**
     * 秘书、助理进行特殊处理
     *
     * @param wordList
     * @param assistantDic 助理秘书词库
     * @param managerDic   经理 总经理词库
     * @param s
     * @return
     */
    private List<Lexeme> alterwrod(List<Lexeme> wordList, List<String> assistantDic, List<String> managerDic, String s) {
        List<Lexeme> deleteLexemes = new ArrayList<>();
        for (String s1 : assistantDic) {
            if (s.contains(s1)) {
                for (String s2 : managerDic) {
                    if (s.contains(s2)) {
                        int begin = s.indexOf(s2);
                        Lexeme newLexeme = new Lexeme(0, begin, begin + s2.length() + s1.length(), 4);
                        newLexeme.setLexemeText(s2 + s1);
                        wordList.add(newLexeme);
                        for (Lexeme lexeme : wordList) {
                            if (s2.equals(lexeme.getLexemeText())) {
                                deleteLexemes.add(lexeme);
                            }
                        }
                    }
                }
            }
        }
        for (Lexeme lexeme : deleteLexemes) {
            wordList.remove(lexeme);
        }
        return wordList;
    }

    /**
     * 删除连词
     *
     * @param wordList
     * @param conjunctionDic
     * @return
     */
    private List<Lexeme> conjunction(List<Lexeme> wordList, List<String> conjunctionDic) {
        List<Lexeme> deleteLexemes = new ArrayList<>();
        for (Lexeme lexeme : wordList) {
            if (conjunctionDic.contains(lexeme.getLexemeText())) {
                deleteLexemes.add(lexeme);
            }
        }
        for (Lexeme lexeme : deleteLexemes) {
            wordList.remove(lexeme);
        }
        return wordList;
    }
}
