package com.zhc.gal.exception;

import java.io.Serializable;

/**
 * 表示在应用程序中无法解析类型的情况。
 *
 * @author yinger
 * @date 2024/1/1
 */
public class UnableResolveTypeException extends Exception implements Serializable {
    /**
     * 创建一个UnableResolveTypeException实例。
     *
     * @param message 描述无法解析类型的具体原因。
     */
    public UnableResolveTypeException(String message) {
        super(message);
    }
}
