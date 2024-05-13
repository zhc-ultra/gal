package com.zhc.gal.utils;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * @author zhc
 * @description
 * @date 2024/5/13 15:34
 **/
public class SortUtil {
    /**
     * TODO 后续再提供一个归并排序 - 稳定排序
     */
    private static int first = 0;
    public static int last = 0;

    public static void sort(Object arr, Comparator comparator) {
        first = last = 0;
        int len = Array.getLength(arr);
        quickSort(arr, 0, len - 1, comparator);
    }

    public static void quickSort(Object array, int l, int r, Comparator comparator) {
        if (l >= r) {
            return;
        }
        Object x = Array.get(array, l + (int) (Math.random() * (r - l + 1)));
        partition(array, l, r, x, comparator);
        int left = first;
        int right = last;
        quickSort(array, l, left - 1, comparator);
        quickSort(array, right + 1, r, comparator);
    }

    public static void partition(Object array, int l, int r, Object x, Comparator c) {
        first = l;
        last = r;
        int i = l;
        while (i <= last) {
            if (c.compare(Array.get(array, i), x) == 0) {
                i++;
            } else if (c.compare(Array.get(array, i), x) < 0) {
                swap(array, first++, i++);
            } else {
                swap(array, i, last--);
            }
        }
    }

    public static void swap(Object arr, int i, int j) {
        Object tmp = Array.get(arr, i);
        Array.set(arr, i, Array.get(arr, j));
        Array.set(arr, j, tmp);
    }
}
