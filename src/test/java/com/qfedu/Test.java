package com.qfedu;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Test {
    public static void main(String[] args) {
        LinkedHashMap<String,String> l = new LinkedHashMap<String,String>();
        l.put("aaa","xxx");
        l.put("bbb","xxx");
        l.put("fdff","xxx");
        /*for (Map.Entry<String, String> stringStringEntry : l.entrySet()) {
            System.out.println(stringStringEntry.getKey());
            System.out.println(stringStringEntry.getValue());
        }*/

        //l.forEach((s, s2) -> System.out.println(s + ">>>>>>" + s2));

        TreeMap<String, String> t = new TreeMap<>(new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        t.put("dddd","d");
        t.put("cdd","c");
        t.put("bd","b");
        t.put("1adasdasd","a");

        t.forEach((s, s2) -> System.out.println(s + ">>>>>>" + s2));

    }


}
