package com.zhc.gal.utils;

import lombok.Data;

/**
 * @author yinger
 * @description 数据范围
 * @date 2024/1/1
 **/
@Data
public class Range {
    public static final Range initial = new Range(0, 1);
    private double min;
    private double max;

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * 生成指定范围内的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 生成的随机数
     */
    private static double getNumberMinToMax(double min, double max) {
        if (min == max) {
            return min;
        }

        if (min > max) {
            double t = min;
            min = max;
            max = t;
        }
        return (Math.random() * (max - min + 1)) + min;
    }

    public static double getNumberMinToMax(Range range) {
        return getNumberMinToMax(range.getMin(), range.getMax());
    }
}
