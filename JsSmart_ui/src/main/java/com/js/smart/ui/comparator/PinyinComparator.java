package com.js.smart.ui.comparator;


import java.util.Comparator;
import java.util.Map;

/**
 * 比较器
 */
public class PinyinComparator implements Comparator<Map<String, Object>> {

    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        if (o1.get("initial").equals("@")
                || o2.get("initial").equals("#")) {
            return -1;
        } else if (o1.get("initial").equals("#")
                || o2.get("initial").equals("@")) {
            return 1;
        } else {
            return ((String)o1.get("initial")).compareTo(((String)o2.get("initial")));
        }
    }

}