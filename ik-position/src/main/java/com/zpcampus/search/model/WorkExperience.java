package com.zpcampus.search.model;

/**
 * Created by len.zhang on 2015/12/31.
 */
public class WorkExperience {
    //是否需要工作经验（0无经验 1有经验 2有无经验均可）
    private int num;
    //经验类型（1无具体工作经验年限要求 2工作经验年限要求为一个范围 3工作经验年限要求为一个）
    private int type;
    //专项经验词
    private String word;
    //工作经验起始
    private int start;
    //工作经验终止
    private int end;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
