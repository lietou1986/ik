package com.zpcampus.util;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by len.zhang on 2014/8/14.
 */
public class CommonUtilTest extends TestCase {

    public void testIsNullOrEmpety() {
        String word = "";
        assertTrue(CommonUtils.isNullOrEmpety(word));
        word = null;
        assertTrue(CommonUtils.isNullOrEmpety(word));
        word = "test";
        assertFalse(CommonUtils.isNullOrEmpety(word));
    }

    public void testInsertMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        CommonUtils.insertMap(map, "中国");
        assert map.get("中国") == 1;
        CommonUtils.insertMap(map, "中国");
        assert map.get("中国") == 2;
    }

    public void testGetMapValue() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        CommonUtils.insertMap(map, "中国");
        assert CommonUtils.getMapValue(map, "中华") == 0;
        assert CommonUtils.getMapValue(map, "中国") == 1;
    }

    public void testIsPinyin() {
        String word = "asdf-sadf =sd";
        assertTrue(CommonUtils.isPinyin(word));
        word = "as山东";
        assertFalse(CommonUtils.isPinyin(word));
    }
}
