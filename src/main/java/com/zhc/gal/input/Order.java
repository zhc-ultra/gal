package com.zhc.gal.input;

import java.util.Comparator;

/**
 * @author zhc
 * @description
 * @date 2024/5/13 15:22
 **/
public class Order {

    private final Comparator<?> comparator;// 默认升序 asc

    /**
     * 排序规则
     *
     * @param order "asc" or "desc"
     */
    public Order(String order) {
        if (order == null) {
            throw new NullPointerException("order is null");
        }
        if ("asc".equals(order)) {
            comparator = Comparator.naturalOrder();
        } else if ("desc".equals(order)) {
            comparator = Comparator.reverseOrder();
        } else {
            throw new IllegalArgumentException("unknown order: " + order);
        }
    }

    /**
     * 自定义比较器
     *
     * @param comparator 比较器
     */
    public Order(Comparator<?> comparator) {
        this.comparator = comparator;
    }
}
