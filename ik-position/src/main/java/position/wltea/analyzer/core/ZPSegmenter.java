/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 */
package position.wltea.analyzer.core;

import com.zpcampus.search.service.WordSegService;
import com.zpcampus.search.service.imp.WordSegServiceImpl;
import position.wltea.analyzer.cfg.Configuration;
import position.wltea.analyzer.cfg.DefaultConfig;
import position.wltea.analyzer.dic.Dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * IK分词器主类
 */
public final class ZPSegmenter {

    //字符窜reader
    private Reader input;
    //分词器配置项
    private Configuration cfg;
    //分词器上下文
    private AnalyzeContext context;

    private List<Lexeme> lexemes;

    private boolean useSmart = false;

    /**
     * IK分词器构造函数
     *
     * @param input
     * @param useSmart 为true，使用智能分词策略
     *                 <p>
     *                 非智能分词：细粒度输出所有可能的切分结果
     *                 智能分词： 合并数词和量词，对分词结果进行歧义判断
     */

    public ZPSegmenter(Reader input, boolean useSmart) {
        this.input = input;
        this.useSmart = useSmart;
        this.cfg = DefaultConfig.getInstance();
        this.init();

    }

    public ZPSegmenter(Reader input) {
        this(input, false);
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化词典单例
        Dictionary.initial(this.cfg);
        //初始化分词上下文
        this.context = new AnalyzeContext(this.cfg);
    }


    /**
     * 初始化词典，加载子分词器实现
     *
     * @return List<ISegmenter>
     */
    private List<ISegmenter> loadSegmenters() {
        List<ISegmenter> segmenters = new ArrayList<ISegmenter>(4);
        //处理字母的子分词器
        segmenters.add(new LetterSegmenter());
        //处理中文数量词的子分词器
        segmenters.add(new CN_QuantifierSegmenter());
        //处理中文词的子分词器
        segmenters.add(new CJKSegmenter());
        return segmenters;
    }

    /**
     * 分词，获取下一个词元
     *
     * @return Lexeme 词元对象
     * @throws java.io.IOException
     */
    public synchronized Lexeme next() throws IOException {
        Lexeme l = null;
        if (lexemes == null) {
            WordSegService segWordService = new WordSegServiceImpl();
            if (this.useSmart)
                lexemes = segWordService.smartSegWord(getKeyWord()).getLexemes();
            else
                lexemes = segWordService.segWord(getKeyWord()).getLexemes();
        }
        if (!lexemes.isEmpty()) {
            l = lexemes.get(0);
            lexemes.remove(0);
        }
        return l;
    }

    private String getKeyWord() {
        BufferedReader r = new BufferedReader(input);
        StringBuilder b = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                b.append(line);
                b.append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        b.toString();
        return b.toString();
    }

    /**
     * 重置分词器到初始状态
     *
     * @param input
     */
    public synchronized void reset(Reader input) {
        this.input = input;
        context.reset();
        if (null != lexemes) {
            lexemes = null;
        }
    }
}
