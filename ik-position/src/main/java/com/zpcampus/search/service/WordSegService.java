package com.zpcampus.search.service;

import com.zpcampus.search.model.WordModel;

/**
 * Created by len.zhang on 2016/8/24.
 * 自定义分词接口
 */
public interface WordSegService {

    /**
     * 智能分词
     *
     * @param input
     * @return
     */
    WordModel smartSegWord(String input);

    /**
     * 分词
     *
     * @param input
     * @return
     */
    WordModel segWord(String input);

}
