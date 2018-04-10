package com.zpcampus.search;

import com.zpcampus.search.model.WordModel;
import com.zpcampus.search.service.WordSegService;
import com.zpcampus.search.service.imp.WordSegServiceImpl;

/**
 * Created by len.zhang on 2016/11/1.
 */
public class WordSegMain {
    public static void main(String[] args) {
        WordSegService segWordServer = new WordSegServiceImpl();
        WordModel wordModel1 = segWordServer.smartSegWord("微积分");
        System.out.println(wordModel1.getWord());
        System.out.println(wordModel1.getAlterword());
        System.out.println(wordModel1.getContent());
    }

}
