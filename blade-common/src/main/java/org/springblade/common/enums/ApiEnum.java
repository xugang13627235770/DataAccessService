package org.springblade.common.enums;

/**
 * 接口相关枚举
 */
public enum ApiEnum {

    RESPONSE_CODE_UNFUNCTION("不支持该功能", "604"),
    YITU("依图", "yitu"),
    VCM("华为", "vcm"),
    HAIHUI("海慧", "haihui"),
    YUSHI("宇视", "yushi"),
    KUANGSHI("旷视", "kuangshi"),
    SHANGTANG("商汤", "shangtang"),
    YUNCONG("云从", "yuncong");

    private String name;

    private String index;


    private ApiEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
