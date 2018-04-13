package com.zpcampus.search;

import com.zpcampus.search.model.WordModel;
import com.zpcampus.search.service.WordSegService;
import com.zpcampus.search.service.imp.WordSegServiceImpl;

import java.io.IOException;

/**
 * Created by len.zhang on 2016/11/1.
 */
public class WordSegMain {
    public static void main(String[] args) throws IOException {
        WordSegService wordSegService = new WordSegServiceImpl();
        WordModel wordModel = wordSegService.smartSegWord("欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!");
        System.out.println(wordModel.getWord());
        System.out.println(wordModel.getAlterword());
        System.out.println(wordModel.getContent());
    }

}
