package org.springblade.common.enums;

/**
 * 字典kind
 */
public enum DictEnum {

    PLATETYPE("车牌类型", "10518828"),
    PLATECOLOR("车牌颜色", "426228"),
    VEHICLETYPE("车辆类型", "3769087"),
    VEHICLECOLOR("车身颜色", "12610795"),
    VEHICLEBRAND("车辆品牌", "1612002");

    private String name;

    private String index;


    private DictEnum(String name, String index) {
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
