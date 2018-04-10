package com.zpcampus.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by len.zhang on 2014/8/6.
 */
public class CommonUtils {

    /**
     * 判断一个字符串是否为空
     *
     * @param word 字符串
     * @return 返回boolean类型
     */
    public static boolean isNullOrEmpety(String word) {
        if (null == word || word.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 向Map中插入数据，如果Map存在该数据，value值+1，否则，设置为1
     *
     * @param map  Map集合
     * @param word 用于判断Map的key中是否存在
     * @return Map集合
     */
    public static Map<String, Integer> insertMap(Map<String, Integer> map, String word) {
        if (isNullOrEmpety(word)) {
            return map;
        }
        int cnt = 1;
        if (map.containsKey(word)) {
            cnt += map.get(word);
        }
        map.put(word, cnt);
        return map;
    }

    /**
     * 获取map的int型value值，默认为0
     *
     * @param map  map集合
     * @param word key值
     * @return value值
     */
    public static int getMapValue(Map<String, Integer> map, String word) {
        int value = 0;
        if (null == map) {
            return value;
        }

        if (map.containsKey(word)) {
            value = map.get(word);
        }
        return value;
    }

    /**
     * 判断是否为拼音
     *
     * @param word
     * @return
     */
    public static boolean isPinyin(String word) {
        boolean pinyin = false;
        Pattern pinyinPatt = Pattern.compile("[^\\u4E00-\\u9FA5)]+");
        pinyin = pinyinPatt.matcher(word).matches();
        return pinyin;
    }

    /**
     * 解析URL，并返回访问该URL的结果字符串
     *
     * @param urlAddress 地址
     * @param params     参数
     * @return
     */
    public static String httpPost(String urlAddress, String params) {
        URL url = null;
        HttpURLConnection con = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            url = new URL(urlAddress);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            con.setReadTimeout(2000000);
            con.setConnectTimeout(2000000);
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            byte[] b = params.getBytes();
            con.getOutputStream().write(b, 0, b.length);
            con.getOutputStream().flush();
            con.getOutputStream().close();
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    result.append(line);
                }
            }
        } catch (Exception e) {
            System.out.println("解析URL服务错误!URL:" + urlAddress + "?" + params);
        } finally {
            try {
                // 释放资源，解决问题：
                // java.net.SocketException: No buffer space available (maximum
                // connections reached?): connect
                InputStream is = con.getInputStream();
                is.close();
                if (in != null) {
                    in.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String parse(String word) {
        String result = "";
        Pattern pat = Pattern.compile("\\(.*\\)");
        Matcher mat = pat.matcher(word);
        if (mat.find()) {
            System.out.println(mat.group(0));
        }
        return result;
    }


    public static void main(String[] args) {
        CommonUtils commonUtil = new CommonUtils();
        String url = "http://weizhigang001220.zpcampus.com/solr/position/select";
        String param = "q=*%3A*&fl=SOU_COMPANY_ID&wt=json";
        Set<String> nameSet = new HashSet<String>();

        int start = 0;
        int rows = 100000;
        FileUtils fo = new FileUtils();

        while (true) {
//            param += ("&rows=" + rows + "&start=" + start);
            JSONObject jsonObject = new JSONObject(commonUtil.httpPost(url, param + "&rows=" + rows + "&start=" + start));
            JSONArray nameArrays = jsonObject.getJSONObject("response").getJSONArray("docs");
            for (int i = 0; i < nameArrays.length(); i++) {
                JSONObject nameObj = nameArrays.getJSONObject(i);
                if (nameObj.has("SOU_COMPANY_ID")) {
                    nameSet.add(nameObj.getString("SOU_COMPANY_ID"));
                }
            }
            fo.writeFile("E:\\zp\\公司数据\\input\\company_id.txt", nameSet);
            start += rows;
            System.out.println("处理完毕：" + start);
            if (nameArrays.length() < rows) {
                break;
            }
        }

//        JSONObject jsonObject = new JSONObject(commonUtil.httpPost(url, param));
//        JSONArray nameArrays = jsonObject.getJSONObject("response").getJSONArray("docs");
//        for (int i=0;i<nameArrays.length();i++) {
//            JSONObject nameObj = nameArrays.getJSONObject(i);
//            nameSet.add(nameObj.getString("SOU_COMPANY_NAME"));
//        }
        fo.writeFile("E:\\zp\\公司数据\\input\\company_id_all.txt", nameSet);
    }
}
