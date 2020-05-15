package org.springblade.common.vo;

import lombok.Data;

import java.util.List;

/**
 * Excel导出配置
 */
@Data
public class ExcelExportSetting {

    /**
     * 页签名称
     */
    private String sheetName;

    /**
     * 表头名称数组
     */
    private String [] cellTitleArray;

    /**
     * 数据数组集合
     */
    private List<String[]> dataList;

    public ExcelExportSetting() { }

    public ExcelExportSetting(String sheetName, String[] cellTitleArray, List<String[]> dataList) {
        this.sheetName = sheetName;
        this.cellTitleArray = cellTitleArray;
        this.dataList = dataList;
    }
}
