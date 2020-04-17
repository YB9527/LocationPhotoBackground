package com.xupu.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

/**
 * 一般工具类
 */
public class Tool {
    /**
     * 检查集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty() ? true : false;
    }

    /**
     * 获取本机ip地址
     *
     * @return
     */
    public static String getHostAddress() {
        String hostAddress = "http://192.168.2.47:8080/";
        return hostAddress;
    }


    /**
     * 检查文件夹是否存在
     *
     * @param dirPath   文件路径
     * @param isCreated 如果没有是否创建
     */
    public static boolean exitsDir(String dirPath, boolean isCreated) {
        File file = new File(dirPath);
        if (file.exists()) {
            return true;
        } else {
            if (isCreated) {
                boolean bl = file.mkdirs();
                return bl;
            }
        }
        return false;
    }
    private static Gson gson;

    /**
     * 得到 只处理 @Expose json对象
     * @return
     */
    public static Gson getGson(){
        if(gson == null){
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .create();
        }
        return  gson;
    }


    public static boolean isEmpty(String str) {
        return str == null ? true:str.isEmpty();
    }
}
