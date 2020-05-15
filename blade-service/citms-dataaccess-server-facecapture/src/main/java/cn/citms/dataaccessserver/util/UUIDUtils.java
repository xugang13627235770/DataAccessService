package cn.citms.dataaccessserver.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 生成UUID
 */
@Slf4j
public class UUIDUtils {

    /**
     * 生成UUID
     * @return
     */
    public static String createUUID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 生成执行个数的UUID
     * parameter  num
     * @return
     */
    public static String[] createUUIDs(int num){

        if( num <= 0)
            return null;

        String[] uuidArr = new String[num];

        for (int i = 0; i < uuidArr.length; i++) {
            uuidArr[i] = createUUID();
        }

        return uuidArr;
    }

//    public static void  main(String[] args){
//        String uuid = createUUID();
//        System.out.print(uuid);
//    }

}
