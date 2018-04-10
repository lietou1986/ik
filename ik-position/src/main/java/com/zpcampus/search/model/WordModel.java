package com.zpcampus.search.model;

import position.wltea.analyzer.core.Lexeme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by len.zhang on 2015/12/24.
 */
public class WordModel {
    //原词内容
    private String word;
    //更改原词内容
    private String alterword;
    //分词后的结果
    private String content;
    //分词的内容
    private List<Lexeme> lexemes;
    //工作经验
    private WorkExperience workExperience;
    //薪资
    private WorkSalary workSalary;
    //工作地点
    private WorkCity workCity;
    //学历水平
    private WorkEduLevel workEduLevel;
    //公司性质
    private WorkCompany workCompany;
    //工作性质
    private WorkType workType;
    //工作标签
    private WorkLabel workLabel;

    public WordModel(String word) {
        this.word = word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public String getAlterword() {
        return alterword;
    }

    public void setAlterword(String alterword) {
        this.alterword = alterword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WordModel() {
        lexemes = new ArrayList<>();
    }

    public List<Lexeme> getLexemes() {
        return lexemes;
    }

    public void setLexemes(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(WorkExperience workExperience) {
        this.workExperience = workExperience;
    }

    public WorkSalary getWorkSalary() {
        return workSalary;
    }

    public void setWorkSalary(WorkSalary workSalary) {
        this.workSalary = workSalary;
    }

    public WorkCity getWorkCity() {
        return workCity;
    }

    public void setWorkCity(WorkCity workCity) {
        this.workCity = workCity;
    }

    public WorkEduLevel getWorkEduLevel() {
        return workEduLevel;
    }

    public void setWorkEduLevel(WorkEduLevel workEduLevel) {
        this.workEduLevel = workEduLevel;
    }

    public WorkCompany getWorkCompany() {
        return workCompany;
    }

    public void setWorkCompany(WorkCompany workCompany) {
        this.workCompany = workCompany;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public WorkLabel getWorkLabel() {
        return workLabel;
    }

    public void setWorkLabel(WorkLabel workLabel) {
        this.workLabel = workLabel;
    }
}
