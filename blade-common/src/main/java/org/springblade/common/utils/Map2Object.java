package org.springblade.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/17 0017.
 */
public class Map2Object {

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }


    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.get(obj) ==  null ) continue;
            if (field.getType().toString().contains("java.lang.String")){
                map.put(field.getName(), field.get(obj));
            }else {
//                String array = JSON.toJSONString(field.get(obj));
//                map.put(field.getName(), array);
            }
        }
        return map;
    }


    /**
     * 遍历对象获取非空kv
     * @param object
     * @return
     */
    public static Map<String,Object> getMap(Object object){
        Map<String,Object> map = new HashMap<>();
        Field[] field = object.getClass().getDeclaredFields();
        for(int j=0 ; j<field.length ; j++){
            String name = field[j].getName();
            name = name.substring(0,1).toUpperCase()+name.substring(1);
            String type = field[j].getGenericType().toString();
            if(type.equals("class java.lang.String")){
                Method m;
                String value;
                try {
                    m = object.getClass().getMethod("get"+name);
                    value = (String) m.invoke(object);
                    if(value != null && !"".equals(value)){
//                        System.out.println(name);
//                        System.out.println(value);
                        map.put(name ,value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return map;
    }

}
