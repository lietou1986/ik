package com.zpcampus.search.conf;

/**
 * Created by len.zhang on 2015/12/31.
 */
public interface Configuration {
//    /**
//     * 获取主分词器词库
//     * @return
//     */
//    public String getMainDic();
//    /**
//     * 获取城市词路径
//     * @return String 城市词路径
//     */
//    public String getCityDicionary();

    /**
     * 获取城区词路径
     *
     * @return String 获取城区词路径
     */
    public String getRegionDicionary();

    /**
     * 获取"工程师"类词库路径
     *
     * @return String "工程师"类词库路径
     */
    public String getTheoremDicionary();

    /**
     * 获取"总工程师"类词库路径
     *
     * @return String "总工程师"类词库路径
     */
    public String getEngineerDicionary();

    /**
     * 获取垃圾类词库路径
     *
     * @return String 垃圾类词库路径
     */
    public String getConjunctionDicionary();

    /**
     * 获取多义类词库路径
     *
     * @return String 多义词库路径
     */
    public String getPolysemyDicionary();

    /**
     * 获取多职位前词库路径
     *
     * @return String 多职位前词库路径
     */
    public String getManagerDicionary();

    /**
     * 获取多职位后词库路径
     *
     * @return String 多职位后词库路径
     */
    public String getAssistantDicionary();

    /**
     * 获取数字词库路径
     *
     * @return String 数字词库路径
     */
    public String getNumberDicionary();

    /**
     * 获取无用词词库路径
     *
     * @return String 无用词词库路径
     */
    public String getWorthlessDicionary();

    /**
     * 获取标签词词库路径
     *
     * @return String 标签词词库路径
     */
    public String getTagDicionary();

    /**
     * 获取公司性质词词库路径
     *
     * @return String 公司性质词词库路径
     */
    public String getCorporationDicionary();
//    /**
//     * 获取ik分词器的主路径
//     * @return
//     */
//    public String getIkMainFilePath();

}
