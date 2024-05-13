package com.zhc.gal.exception;


/**
 * @author yinger
 * @description TODO
 * @date 2024/1/1
 **/
public class UnknownTypeException extends Exception {
    /**
     * 表示在处理类型时遇到未知类型的异常。
     * 此异常通常在尝试对类型执行非法操作或期望类型与实际类型不匹配时抛出。
     *
     * @param message
     */
    public UnknownTypeException(String message) {
        super(message);
    }
}
