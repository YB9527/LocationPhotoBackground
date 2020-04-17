package com.xupu.common;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectTool {

    private static  ReflectTool reflectTool;
    public static ReflectTool getInstance(){
        if(reflectTool == null){
            reflectTool = new ReflectTool();
        }
        return  reflectTool;
    }
    /**
     *
     * @param methodName map 的主键值 ，T对象的 methodName 不允许重复
     * @param list
     * @param <T>
     * @return
     */
    public  static  <T> Map<String, T> getIDMap(String methodName,List<T> list) {
        Map<String, T> map = new HashMap<>();
        if(Tool.isEmpty(list)){
            return map;
        }
        Class tClass  = list.get(0).getClass() ;
        Method m=null;
        try {
            m =  tClass.getMethod(methodName);
            for (T t :list
            ) {
               String key =  m.invoke(t,null).toString();
                map.put(key,t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 包含 get方法 、 set方法集合
     */
    public  static class  MethodCustom<T>{
        public  MethodCustom(){
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            getMethods =new ArrayList<>();
            setMehods =new ArrayList<>();
            Method[] ms = tClass.getMethods();
            for (Method m :
                    ms) {
                String name = m.getName();
                if(name.startsWith("get")){
                    getMethods.add(m);
                }else if(name.startsWith("set")){
                    setMehods.add(m);
                }
            }
        }

        private List<Method> getMethods;
        private  List<Method> setMehods;

        public List<Method> getGetMethods() {
            return getMethods;
        }

        public void setGetMethods(List<Method> getMethods) {
            this.getMethods = getMethods;
        }

        public List<Method> getSetMehods() {
            return setMehods;
        }

        public void setSetMehods(List<Method> setMehods) {

            this.setMehods = setMehods;
        }

    }

    /**
     * 创建实例化对象
     * @param <T>
     * @return
     */
    public static  <T> T getInstanceOfT()
    {

        ParameterizedType superClass =  (ParameterizedType) getInstance().getClass().getGenericSuperclass();
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
        try
        {
            return type.newInstance();
        }
        catch (Exception e)
        {
            // Oops, no default constructor
            throw new RuntimeException(e);
        }
    }



}
