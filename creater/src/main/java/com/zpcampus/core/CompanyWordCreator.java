package com.zpcampus.core;

import com.zpcampus.model.TermFrequency;
import com.zpcampus.util.CommonUtils;
import com.zpcampus.util.FileUtils;
import company.wltea.analyzer.core.IKSegmenter;
import company.wltea.analyzer.core.Lexeme;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by len.zhang on 8/19.
 */
public class CompanyWordCreator {

    /**
     * 获取高频词
     *
     * @param datas 原始词
     * @return 高频词集合
     */
    private Map<String, Integer> getIKFreWord(Set<String> datas) {
        Map<String, Integer> term2frequency = new HashMap<String, Integer>();
        try {
            for (String data : datas) {
                IKSegmenter ikSeg = new IKSegmenter(new StringReader(data), false);
                Lexeme lexeme;
                while (null != (lexeme = ikSeg.next())) {
                    //高频词仅取长度大于1的词
                    if (lexeme.getLexemeText().length() > 1) {
                        String term = lexeme.getLexemeText();
                        if (!CommonUtils.isPinyin(term)) {
                            term2frequency = CommonUtils.insertMap(term2frequency, lexeme.getLexemeText());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return term2frequency;
    }

    /**
     * 通过词元频率重新对词进行分词，并重新获取高频词
     *
     * @param datas          原始数据
     * @param term2frequency 词元频率
     * @return
     */
    private Map<String, Integer> getHighFreWord(Set<String> datas, Map<String, Integer> term2frequency) {
        Map<String, Integer> highFreWordMap = new HashMap<String, Integer>();
        for (String data : datas) {
            List<Lexeme> LexemeList = smartAnalyzer(data, term2frequency);
            for (Lexeme lexeme : LexemeList) {
                highFreWordMap = CommonUtils.insertMap(highFreWordMap, lexeme.getLexemeText());
            }
        }
        return highFreWordMap;
    }

    /**
     * 正向最大分词,获取频率
     *
     * @param datas          待分词的词语
     * @param highFreWordMap 词元频率
     * @param minFre         最小需满足的频率
     * @return 分词结果
     */
    private Set<String> fmmSegWords(Set<String> datas, Map<String, Integer> highFreWordMap, int minFre) {
        Set<String> resultList = new HashSet<String>();
        for (String data : datas) {
            resultList.addAll(fmmSegWord(data, highFreWordMap, minFre));
        }
        return resultList;
    }

    /**
     * 通过IK分词后，合并单字方式获取新词
     *
     * @param datas          输入名称
     * @param highFreWordMap 词元频率
     * @return 新词
     */
    private Set<String> getNewWords(Set<String> datas, Map<String, Integer> highFreWordMap) {
        Set<String> results = new HashSet<String>();
        for (String data : datas) {
            results.addAll(getNewWord(data, highFreWordMap));
        }
        return results;
    }

    /**
     * 通过IK分词后，合并单字方式获取新词
     *
     * @param data           输入名称
     * @param highFreWordMap 词元频率
     * @return 新词
     */
    private Set<String> getNewWord(String data, Map<String, Integer> highFreWordMap) {
        Set<String> results = new HashSet<String>();
        if (data.length() < 1) {
            return results;
        }
        List<Lexeme> lexemeList = smartAnalyzer(data, highFreWordMap);

        if (data.length() == 4) {
            Set<String> tempNewWord = new HashSet<String>();
            tempNewWord.add(data.substring(0, 2));
            tempNewWord.add(data.substring(2, 4));
            for (Lexeme lexeme : lexemeList) {
                if (tempNewWord.contains(lexeme.getLexemeText())) {
                    tempNewWord.remove(lexeme.getLexemeText());
                }
            }
            if (!tempNewWord.isEmpty()) {
                results.addAll(tempNewWord);
            }
            return results;
        }

        for (int i = 0; i < lexemeList.size(); i++) {
            String newWord = "";
            Lexeme lexeme = lexemeList.get(i);
            if (lexeme.getLexemeText().length() == 1) {
                //输入为2元或3元，且分词后结果与原词不同，则原词为新词
                if (data.length() <= 3 && lexeme.getLexemeText().length() < data.length()) {
                    results.add(data);
                    break;
                }
                int nextIdx = i + 1;
                while (nextIdx < lexemeList.size()) {
                    Lexeme nextLexeme = lexemeList.get(nextIdx);
                    if (nextLexeme.getLexemeText().length() == 1 &&
                            nextLexeme.getBegin() == lexeme.getBegin() + newWord.length()) {
                        newWord += nextLexeme.getLexemeText();
                        i++;
                    }
                    nextIdx++;
                }
                if (!CommonUtils.isNullOrEmpety(newWord)) {
                    results.add(newWord);
                }
                if (newWord.length() > 3) {
                    System.out.println(newWord);
                }
            }
        }


        return results;
    }

    /**
     * 正向最大分词
     *
     * @param word           待分词的词语
     * @param term2frequency 词元频率
     * @param minFre         最小需满足的频率
     * @return 分词结果
     */
    private Set<String> fmmSegWord(String word, Map<String, Integer> term2frequency, int minFre) {
        Set<String> resultList = new HashSet<String>();
        String w = "";
        int len = 0;
        // 记录未分词成功的词
        String errorWord = "";
        while (!CommonUtils.isNullOrEmpety(word)) {
            len = word.length();
            w = word.substring(0, len);
            int fre = 0;
            while (len > 0) {
                if (len == 1) {
                    errorWord += w;
                    break;
                } else if (term2frequency.containsKey(w)) {
                    fre = term2frequency.get(w);
                    if (!"".equals(errorWord) && fre > minFre) {
                        resultList.addAll(parse(errorWord));
                        errorWord = "";
                    }
                    if (fre > minFre)
                        break;
                }
                len -= 1;
                w = word.substring(0, len);
            }
            // 去掉已经匹配的词
            word = word.substring(len, word.length());
        }
        if (!errorWord.equals("")) {
            resultList.addAll(parse(w));
        }
        return resultList;
    }

    /**
     * 添加区域词的词频
     *
     * @param highFreWordMap
     * @param minFre
     * @return
     */
    private Map<String, Integer> addRegionFre(Map<String, Integer> highFreWordMap, int minFre) {

        Iterator<String> keyIter = highFreWordMap.keySet().iterator();
        while (keyIter.hasNext()) {
            String keyWord = keyIter.next();

            if (isRegion(keyWord)) {
                highFreWordMap.put(keyWord, highFreWordMap.get(keyWord) + minFre);
            }
        }

        return highFreWordMap;
    }

    /**
     * 判断是否为区域词
     *
     * @param word
     * @return
     */
    private boolean isRegion(String word) {
        boolean isRegion = false;
        if (CommonUtils.isNullOrEmpety(word) || word.length() < 3) {
            return isRegion;
        }
        if (word.endsWith("省") || word.endsWith("市") || word.endsWith("区") ||
                word.endsWith("自治州") || word.endsWith("自治县")) {
            isRegion = true;
        }
        return isRegion;
    }

    private List<String> parse(String word) {
        List<String> terms = new ArrayList<String>();
        if (!CommonUtils.isPinyin(word)) {
            word = word.replaceAll("\\）", "\\)").replaceAll("\\（", "\\(");
            String[] segWordTerms = word.split("\\(");
            for (String segWordTerm : segWordTerms) {
                String[] segTerms = segWordTerm.split("\\)");
                for (String segTerm : segTerms) {
                    terms.add(segTerm);
                }
            }
        }
        return terms;
    }

    /**
     * 根据词元频率纠错的分词结果
     *
     * @param word           进行分词的关键字
     * @param term2frequency 词元频率集合
     * @return 分词结果
     */
    private List<Lexeme> smartAnalyzer(String word, Map<String, Integer> term2frequency) {
        //IK分词集合
        List<Lexeme> ikSegList = getIKLexeme(word);

        int realEnd = 0;
        for (int ikSegIdx = 0; ikSegIdx < ikSegList.size(); ikSegIdx++) {
            realEnd = Math.max(realEnd, ikSegList.get(ikSegIdx).getEndPosition());
            if (ikSegIdx < ikSegList.size() - 1 &&
                    ikSegList.get(ikSegIdx + 1).getBegin() <= ikSegList.get(ikSegIdx).getBegin() &&
                    ikSegList.get(ikSegIdx + 1).getEndPosition() > ikSegList.get(ikSegIdx).getEndPosition()) {
                ikSegList.remove(ikSegIdx);
                ikSegIdx--;
            }
            ikSegList = smartLexeme(ikSegList, term2frequency, ikSegIdx, realEnd);
        }
        ikSegList = uniqSegment(ikSegList);

        return ikSegList;
    }

    private List<Lexeme> getIKLexeme(String data) {
        List<Lexeme> lexemes = new ArrayList<Lexeme>();
        IKSegmenter ik = new IKSegmenter(new StringReader(data), false);
        Lexeme lexeme;
        try {
            while (null != (lexeme = ik.next())) {
                lexemes.add(lexeme);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lexemes;
    }

    /**
     * 获取当前词元后面的词元出现的频率
     *
     * @param nextIdx        下个词元的位置
     * @param ikSegments     分词结果
     * @param term2frequency 词元频率集合
     * @return 词元的频率
     */
    private TermFrequency getNextFrequency(int nextIdx, List<Lexeme> ikSegments,
                                           Map<String, Integer> term2frequency) {
        TermFrequency termFrequencyObj = new TermFrequency();

        if (nextIdx >= ikSegments.size()) {
            return termFrequencyObj;
        }

        Lexeme ikSeg = ikSegments.get(nextIdx);
        int nextEnd = 0;
        int nextFrequency = 0;
        String nextTerm = "";


        while (nextIdx < ikSegments.size()) {
            Lexeme nextLexeme = ikSegments.get(nextIdx);
            if (nextEnd == 0) {
                nextEnd = nextLexeme.getEndPosition();
            }

            if (nextLexeme.getBegin() >= nextEnd) {
                break;
            }

            if (nextLexeme.getBegin() == ikSeg.getBegin()) {
                nextFrequency += CommonUtils.getMapValue(term2frequency, nextLexeme.getLexemeText());
                //记录后面最长的词元
                if (nextLexeme.getLexemeText().length() > nextTerm.length()) {
                    nextTerm = nextLexeme.getLexemeText();
                }
            }
            nextIdx++;
        }
        termFrequencyObj.setTerm(nextTerm);
        termFrequencyObj.setFrequency(nextFrequency);
        return termFrequencyObj;
    }

    /**
     * 判断是否为“兵科|科技|技术发展”这种交叉分词情况
     *
     * @param ikSegList 分词结果
     * @param ikSegIdx  判断词元的位置
     * @return 调整后的分词结果
     */
    private boolean isCross(List<Lexeme> ikSegList, int ikSegIdx) {

        boolean isCross = false;

        Lexeme currentLexeme = ikSegList.get(ikSegIdx);
        int nextIdx = ikSegIdx + 1;
        Lexeme nextLexeme = ikSegList.get(nextIdx);
        //终止的索引位置
        int endIdx = 0;
        //终止的字符位置
        int endPosition = 0;
        //兵科|科技|技术发展
        while (nextIdx < ikSegList.size() - 1) {
            Lexeme nextNextLexeme = ikSegList.get(nextIdx + 1);
            if (nextNextLexeme.getBegin() == currentLexeme.getEndPosition() &&
                    nextNextLexeme.getLexemeText().length() > 1) {
                endIdx = nextIdx;
                endPosition = nextNextLexeme.getBeginPosition();
                isCross = true;
                break;
            } else if (nextNextLexeme.getBegin() > currentLexeme.getEndPosition()) {
                break;
            }
            nextIdx++;
        }
        //技术发展|技术|发展有限公司|发展|有限公司
        //去除发展有限公司
        if (isCross) {
            for (int i = ikSegIdx + 1; i <= endIdx; i++) {
                nextLexeme = ikSegList.get(i);
                if (nextLexeme.getEndPosition() > endPosition) {
                    ikSegList.remove(i);
                    break;
                } else {
                    isCross = false;
                }
            }
        }
        return isCross;
    }

    /**
     * 对分词结果进行去重处理
     *
     * @param ikSegList 分词结果
     * @return 去重后的分词结果
     */
    private List<Lexeme> uniqSegment(List<Lexeme> ikSegList) {
        int end = 0;
        //去除重复
        for (int ikSegIdx = 0; ikSegIdx < ikSegList.size() - 1; ikSegIdx++) {
            Lexeme currentLexeme = ikSegList.get(ikSegIdx);
            Lexeme nextLexeme = ikSegList.get(ikSegIdx + 1);
            if (end >= currentLexeme.getEndPosition() && currentLexeme.getLexemeText().length() == 1) {
                ikSegList.remove(ikSegIdx);
                ikSegIdx--;
                continue;
            }
            if (currentLexeme.getEndPosition() <= nextLexeme.getEndPosition() &&
                    currentLexeme.getBegin() == nextLexeme.getBegin()) {
                ikSegList.remove(ikSegIdx);
                ikSegIdx--;
            }
            end = Math.max(end, currentLexeme.getEndPosition());
        }
        return ikSegList;
    }

    /**
     * 处理分词结果，根据词元出现频率将有歧义的词元重新处理
     *
     * @param ikSegList      IK分词结果
     * @param term2frequency 词元出现频率集合
     * @param ikSegIdx       进行判断的词元的位置
     * @param realEnd        当前词元之前出现的所有词元的最后位置
     *                       如"北京人|北京|人"，"北京"对应的realEnd为3。
     * @return 重新处理的分词结果
     */
    private List<Lexeme> smartLexeme(List<Lexeme> ikSegList, Map<String, Integer> term2frequency,
                                     int ikSegIdx, int realEnd) {
        Lexeme currentLexeme = ikSegList.get(ikSegIdx);

        for (int i = ikSegIdx; i < ikSegList.size() - 1; i++) {
            Lexeme nextLexeme = ikSegList.get(i + 1);
            //下一个词元的开始>=当前词元的结束，表示无交集，跳出循环
            if (nextLexeme.getBegin() >= currentLexeme.getEndPosition()) {
                break;
            }
            //后面词元包含当前词元,将当前词元删除
            if (nextLexeme.getBegin() <= currentLexeme.getBegin() &&
                    nextLexeme.getEndPosition() > currentLexeme.getEndPosition()) {
                ikSegList.remove(ikSegIdx);
                break;
            }
            //判断是否有交叉情况
            if (isCross(ikSegList, ikSegIdx)) {
                i--;
                continue;
            }

            if (realEnd > nextLexeme.getBegin() &&
                    nextLexeme.getLexemeText().length() == 1) {
                ikSegList.remove(i + 1);
                i--;
            } else if (currentLexeme.getEndPosition() > nextLexeme.getBegin() &&
                    currentLexeme.getEndPosition() < nextLexeme.getEndPosition() &&
                    realEnd < nextLexeme.getEndPosition()) {
                //有交集的情况
                //判断下一个词元是否与后面词元有交集
                ikSegList = smartLexeme(ikSegList, term2frequency, ikSegIdx + 1,
                        Math.max(realEnd, nextLexeme.getEndPosition()));
                //重新获取当前词元以及后面词元
                currentLexeme = ikSegList.get(ikSegIdx);
                if (i >= ikSegList.size() - 1) {
                    continue;
                }
                nextLexeme = ikSegList.get(i + 1);

                if (currentLexeme.getEndPosition() >= nextLexeme.getEndPosition()) {
                    continue;
                }


                int currentFrequency = CommonUtils.getMapValue(term2frequency, currentLexeme.getLexemeText());
                //获取下一个词元的频率
                int nextFrequency = getNextFrequency(i + 1, ikSegList, term2frequency).getFrequency();

                //判断前后两个词的频率，取频率较高的词为最终分词结果
                if (currentFrequency > nextFrequency) {
                    Lexeme tempNextLexeme = new Lexeme(0, nextLexeme.getBegin(), 0, 0);

                    if (currentLexeme.getEndPosition() <= nextLexeme.getBegin()) {
                        ikSegList.remove(ikSegIdx + 1);
                        i--;
                    } else {
                        String tempNextTerm = nextLexeme.getLexemeText().substring(currentLexeme.getEndPosition() -
                                nextLexeme.getBegin());
                        tempNextLexeme.setLexemeText(tempNextTerm);
                        tempNextLexeme.setBegin(currentLexeme.getEndPosition());
                        ikSegList.set(i + 1, tempNextLexeme);
                    }
                } else if (currentFrequency < nextFrequency) {
                    Lexeme tempCurrentLexeme = new Lexeme(0, currentLexeme.getBegin(), 0, 0);

                    if (currentLexeme.getBegin() >= nextLexeme.getBegin() ||
                            nextLexeme.getBegin() >= currentLexeme.getEndPosition()) {
                        ikSegList.remove(ikSegIdx);
                        i--;
                    } else {
                        String tempCurrentTerm = currentLexeme.getLexemeText().substring(0,
                                nextLexeme.getBegin() - currentLexeme.getBegin());
                        tempCurrentLexeme.setLexemeText(tempCurrentTerm);
                        ikSegList.set(ikSegIdx, tempCurrentLexeme);
                        currentLexeme = tempCurrentLexeme;
                    }
                }
            }
        }
        return ikSegList;
    }

    public void createNewWord(String inputPath, String outPath, int minFre) {
        File[] files = new File(inputPath).listFiles();
        Set<String> dataList = new HashSet<String>();


        long startTime = System.currentTimeMillis();
        //读取所有公司名称
        FileUtils fo = new FileUtils();
        for (File file : files) {
            dataList.addAll(fo.readFile(file.getPath()));
        }
        long readFileTime = System.currentTimeMillis();
        System.out.println("读取公司名称文件结束，耗时：" + (readFileTime - startTime));
        //利用IK分词，获取词元频率
        Map<String, Integer> term2frequency = getIKFreWord(dataList);
        //将词频率写入文件
        fo.writeFile(outPath + "\\dic.txt", term2frequency);
        long getFreTime = System.currentTimeMillis();
        System.out.println("获取词频字典结束，耗时：" + (getFreTime - readFileTime));
        //重新获取高频词
        Map<String, Integer> highFreWordMap = getHighFreWord(dataList, term2frequency);
        highFreWordMap = addRegionFre(highFreWordMap, minFre);
        fo.writeFile(outPath + "\\highFreDic.txt", highFreWordMap);
        long getHighFreTime = System.currentTimeMillis();
        System.out.println("获取高频词结束，耗时：" + (getHighFreTime - getFreTime));
        //正向最大匹配，未匹配成功的词
        Set<String> results = fmmSegWords(dataList, highFreWordMap, minFre);
        fo.writeFile(outPath + "\\tempWords.txt", results);
        Set<String> finalResults = getNewWords(results, highFreWordMap);
        fo.writeFile(outPath + "\\newWords.txt", finalResults);
        System.out.println("获取新词结束，耗时：" + (System.currentTimeMillis() - getHighFreTime));
    }

    public static void main(String[] args) {
        String inputPath = CompanyWordCreator.class.getResource("/").getPath() + "input";
        String outPath = CompanyWordCreator.class.getResource("/").getPath() + "out";
        int minFre = 100;
        CompanyWordCreator comWordCreator = new CompanyWordCreator();
        comWordCreator.createNewWord(inputPath, outPath, minFre);
    }

}
