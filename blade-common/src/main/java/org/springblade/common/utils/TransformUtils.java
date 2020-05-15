package org.springblade.common.utils;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransformUtils {

    /**
     * 将double类型的数字 转换成 字符串 （包括科学计数法的double） 如果参数为null 返回空串""
     * @param d
     * @return
     */
    public static String DoubleToStr(Double d){
        if(d==null){
            return "";
        }
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(12);
        return nf.format(d);
    }
    /**
     * 表达式转参数List 如：(PlateNo=鄂A*12)and(PlateColor=3)会转换成一个List
     * 包含一个Map map 有2个key-value 根据or来区分是否为多个map
     * @param parameterStr
     * @return
     */
    public static Set<Map<String, String>> expressieToList(String parameterStr) {
        if(StringUtils.isEmpty(parameterStr)){
            return null;
        }
        Set<Map<String,String>> lists= new HashSet<>();
        String[] str = parameterStr.split("\\)\\s*or\\s*\\(");
        if(str==null){
            return null;
        }
        for(String s : str){
            String[] andarr = s.split("\\)\\s*and\\s*\\(");
            Map<String,String> paraMap = new HashMap<>();
            for(String and : andarr){
                and = removeFirstOrLastBracket(and);
                String[] keyValues = and.split("=");
                if(keyValues.length==2){
                    paraMap.put(keyValues[0],keyValues[1]);
                }
            }
            lists.add(paraMap);
        }
        return lists;
    }
    public static String removeFirstOrLastBracket(String text){
        if(StringUtils.isEmpty(text)){
            return null;
        }
        if(text.startsWith("(")){
            text = text.substring(1);
        }
        if(text.endsWith(")")){
            text = text.substring(0,text.length()-1);
        }
        return text;
    }
}
