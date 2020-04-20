package com.xupu.common.tools;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class SpringBootTool {
    private static String rootDir = null;

    /**
     * 得到项目 根路径
     * @return
     */
    public static String getRootDir() {
        if (rootDir == null) {
            try {
                rootDir = ResourceUtils.getURL("classpath:").getPath();
                return rootDir;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return rootDir;
    }
}
