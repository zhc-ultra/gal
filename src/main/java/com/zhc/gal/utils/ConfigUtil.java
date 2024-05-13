package com.zhc.gal.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author zhc
 * @description 读取gla.properties配置文件获取扫描validator注解的路径
 * @date 2024/5/13 16:00
 **/
public class ConfigUtil {
    public static String readPackageName() {
        // 属性文件路径，假设它与你的类路径同级
        String configFilePath = "src/main/resources/gla.properties";
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream(configFilePath)) {
            // 加载属性文件
            prop.load(input);

            // 读取特定键值
            return prop.getProperty("scan.path.validator.annotation");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
