package com.xupu.common.tools;

public class SpringBootTool {
    private static String rootDir = null;

    /**
     * 得到项目 根路径
     *
     * @return
     */
    public static String getRootDir() {
        if (rootDir == null) {

            rootDir = "C:/locationsphoto/";
            return rootDir;

        }
        return rootDir;
    }
}
