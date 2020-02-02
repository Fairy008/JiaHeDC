package com.jh.collection.utils;

import java.util.Comparator;

/**
 * Map按key排序
 */
public class MapKeyComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer str1, Integer str2) {

        return str2.compareTo(str1);
    }
}
