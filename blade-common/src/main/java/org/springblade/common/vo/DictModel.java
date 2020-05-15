package org.springblade.common.vo;

import lombok.Data;

/**
 * 字典数据
 */
@Data
public class DictModel {

    /**
     * 字典编号
     */
    private String dictionaryNo;

    /**
     * 字典字典值
     */
    private String dictionaryValue;

    /**
     * 字典类型
     */
    private String kind;

    private String remark;

    /**
     * 字典id
     */
    private String dictionaryId;

    private String isSystemCN;

}
