package com.zhc.gal.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author yinger
 * @description 封装 Unsafe 操作
 * @date 2024/5/13 15:50
 * <p>
 * -XX:+UnlockExperimentalVMOptions -XX:hashCode=3
 * 0 随机数
 * 1 地址 + 随机数
 * 2 固定值
 * 3 递增数列
 * 4 地址`
 * 对生成样本的 哈希值 进行同一
 * 需要一组样板,和进行统一的 n 组数据
 * 值的注意的是,对于改组数据,数据量不应该大于 Integer.MAX_VALUE
 * 因为采取的 hashCode 选项为3(自增)形式,如果数据量过大,会导致溢出,从而得到不可预知的结果
 * 但是,如果该类重写了HashCode,那么对象头将不再会保存HashCode值,此时我们大可放心的将 id 数据保存在对象头的高32为
 * 多方权衡 后 做出这样的选择
 **/

public class UnsafeUtil {
    private static volatile Unsafe unsafe = null;

    private UnsafeUtil() {
    }

    /**
     * 获取单例的`Unsafe`实例
     * 如果`unsafe`为`null`，则在`UnsafeUtil.class`的同步块中获取`Unsafe`实例并赋值给`unsafe`
     *
     * @return 单例的`Unsafe`实例
     */
    public static Unsafe getUnsafeInstance() {
        // 使用双重检查锁定模式来减少同步的范围
        if (unsafe == null) {
            synchronized (UnsafeUtil.class) {
                if (unsafe == null) {
                    unsafe = getUnsafe();
                }
            }
        }
        return unsafe;
    }

    //         : 25 位 未使用                  31位 hashCode                       1位未使用    4位gc_age  偏向锁标识     2位锁状态
    // example : 0000000000000000000000000    0011011111001101111010111000011    0           0000       0            01
    // 未重写 hashCode 方法时

    /**
     * 从给定对象获取其哈希码的较高位。
     *
     * @param object 待获取哈希码的对象
     * @return 哈希码的高32位，范围为0到Integer.MAX_VALUE
     * @throws IllegalArgumentException 如果传入对象为null
     * @throws IllegalStateException    如果无法获取Unsafe实例或者获取哈希码失败
     */
    public static int hashcode(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Input object cannot be null");
        }
        try {
            // 舍弃低 8 位 非hashcode位
            long code = getUnsafeInstance().getLong(object, (long) 0);
            return (int) (code >>> 8);
        } catch (UnsatisfiedLinkError e) {
            throw new IllegalStateException("Failed to access unsafe instance", e);
        } catch (Throwable t) {
            // Consider logging the error as well
            throw new IllegalStateException("Failed to get code", t);
        }
    }

    /**
     * 使用Unsafe进行内存赋值操作
     *
     * @param object 目标对象
     * @param code   需要赋值的code
     */
    public static void setHashcode(Object object, int code) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        long newValue = ((long) code << 8) + 1;
        try {
            getUnsafeInstance().putOrderedLong(object, 0, newValue); // 使用Unsafe进行内存赋值 操作
        } catch (UnsatisfiedLinkError e) {
            throw e;
        } catch (Throwable t) {
            // UnsatisfiedLinkError是JNI相关的错误
            throw new Error("Unexpected error while accessing unsafe", t);
        }
    }

    /**
     * 将给定的长整型数转换为二进制字符串表示形式
     *
     * @param l 长整型数
     * @return 转换后的二进制字符串
     */
    public static String getLongBinary(long l) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            s.append(((l >> i) & 1) == 0 ? 0 : 1);
        }
        s.reverse();
        return s.toString();
    }

    /**
     * 反射获取Unsafe 实例
     *
     * @return theUnsafe 实例
     */
    public static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Unsafe.theUnsafe field not found", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Illegal access to Unsafe.theUnsafe field", e);
        }
    }
}