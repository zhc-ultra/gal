package com.zhc.gal;

import java.util.Objects;

/**
 * @author zhc
 * @description 三元数对 唯一确定一个类型
 * @date 2024/5/13 15:37
 **/
public class Pairs {
    public int i;
    public int j;
    public int k;

    public Pairs(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pairs acvObj = (Pairs) o;
        return i == acvObj.i && j == acvObj.j && k == acvObj.k;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j, k);
    }

    @Override
    public String toString() {
        return "g[" + i + "][" + j + "][" + k + "]";
    }
}