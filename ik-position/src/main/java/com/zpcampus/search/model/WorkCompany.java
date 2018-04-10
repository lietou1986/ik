package com.zpcampus.search.model;

import java.util.List;

/**
 * Created by len.zhang on 2015/12/31.
 */
public class WorkCompany {
    private int num;
    private List<String> list;

    public WorkCompany(List<String> list) {
        this.list = list;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
