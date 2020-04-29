package com.xupu.common.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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
        return list == null ? true: list.isEmpty();
    }
    /**
     *
     * @param flag null 返回false
     * @return
     */
    public static boolean isEmpty(Boolean flag) {
        return flag == null ? false:flag;
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
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation()
                    .create();
        }
        return  gson;
    }


    public static boolean isEmpty(String str) {
        return str == null ? true : str.trim().isEmpty();
    }
    /** json 转为 对象
     * @param json
     * @param type
     * @return
     */
    public static <T> T jsonToObject(String json, Type type) {
        if(isEmpty(json)){
            return  null;
        }
        T t = getGson().fromJson(json,type);
        return t;
    }
    /**
     *  对象转换为json
     * @param obj
     * @return
     */
    public static  String objectToJson(Object obj){
        return getGson().toJson(obj);
    }

    /**
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list,String connectsymbol) {
        if(Tool.isEmpty(list)){
            return  "";
        }
        StringBuilder sb = new StringBuilder();
        int size = list.size()-1;
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i) + connectsymbol);
        }
        sb.append(list.get(size));
        return  sb.toString();
    }

    /**
     * 检查 map 集合是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        return map == null ? true:map.isEmpty();
    }
}
