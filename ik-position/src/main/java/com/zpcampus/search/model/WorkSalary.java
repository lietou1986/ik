package com.zpcampus.search.model;

/**
 * Created by len.zhang on 2015/12/31.
 */
public class WorkSalary {
    private int num;
    //工资类型
    private int type;
    //专项工资词
    private String word;
    //工资起始
    private int start;
    //工资终止
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
