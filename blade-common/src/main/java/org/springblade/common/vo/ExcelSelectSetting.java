package org.springblade.common.vo;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springblade.common.utils.ExcelCommonUtil;

/**
 * Excel选择框配置对象
 */
@Data
public class ExcelSelectSetting {

    /**
     * 添加下拉框数据的sheet-2003
     */
    HSSFSheet sheet2003;

    /**
     * 添加下拉框数据的sheet-2007
     */
    XSSFSheet sheet2007;

    /**
     * 下拉框数据数组
     */
    String[] optionArray;

    /**
     * 需要设置下拉选项单元格首行下标，从0开始，默认为0
     */
    int firstRowIndex = 0;

    /**
     * 需要设置下拉选项单元格末行下标，默认为65535
     */
    int lastRowIndex = ExcelCommonUtil.EXCEL_MAX_ROW_NUMBER_2003;

    /**
     * 需要设置下拉选项单元格列下标，从0开始，默认为0
     */
    int columnIndex = 0;

    public ExcelSelectSetting() {}

    /**
     * 快捷构造-2003
     *
     * @param sheet 添加下拉框数据的sheet-2003
     * @param optionArray 下拉框数据数组
     * @param firstRowIndex 需要设置下拉选项单元格首行下标，从0开始
     * @param columnIndex 需要设置下拉选项单元格列下标，从0开始
     */
    public ExcelSelectSetting(HSSFSheet sheet, String[] optionArray, int firstRowIndex, int columnIndex) {
        this.sheet2003 = sheet;
        this.optionArray = optionArray;
        this.firstRowIndex = firstRowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * 快捷构造-2007
     *
     * @param sheet 添加下拉框数据的sheet-2007
     * @param optionArray 下拉框数据数组
     * @param firstRowIndex 需要设置下拉选项单元格首行下标，从0开始
     * @param columnIndex 需要设置下拉选项单元格列下标，从0开始
     */
    public ExcelSelectSetting(XSSFSheet sheet, String[] optionArray, int firstRowIndex, int columnIndex) {
        this.sheet2007 = sheet;
        this.optionArray = optionArray;
        this.firstRowIndex = firstRowIndex;
        this.columnIndex = columnIndex;
    }
}
