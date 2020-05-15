package org.springblade.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.springblade.common.vo.ExcelSelectSetting;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Excel模版工具类
 */
public class ExcelTemplateUtil extends ExcelCommonUtil {

    /**
     * Excel列标识数组
     */
    private final static String[] EXCEL_CELL_INDEX_ARRAY = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};

    /**
     * 设置Excel表格下拉选项-2003
     * 新建页签写入字典项引用的方式，解决直接写字典项字符过多报“tring literals in formulas can't be bigger than 255 characters ASCII”错误的问题
     *
     * @param workbook Excel工作簿对象
     * @param essList 下拉选项配置对象集合
     */
    public static void setWorkBookSelectOption(HSSFWorkbook workbook, List<ExcelSelectSetting> essList){
        if(workbook == null || CollectionUtils.isEmpty(essList)){
            return;
        }
        // 获取所有sheet页个数
        int sheetTotal = workbook.getNumberOfSheets();
        // 新建一个sheet页，写入字典数据
        String dictSheet = "dictSheet" + sheetTotal;
        HSSFSheet hiddenSheet = workbook.createSheet(dictSheet);

        for(int i=0; i<essList.size(); i++){
            ExcelSelectSetting ess = essList.get(i);
            String[] optionArray = ess.getOptionArray();

            if(optionArray == null){
                continue;
            }

            for(int j=0; j<optionArray.length; j++){
                // 获取行
                HSSFRow row = hiddenSheet.getRow(j);
                if(row == null){
                    row = hiddenSheet.createRow(j);
                }
                // 设置列值
                HSSFCell cell = row.createCell(i);
                String value = optionArray[j];
                cell.setCellValue(value);
            }
            // 设置模版字典值
            String strFormula = dictSheet + "!$" + EXCEL_CELL_INDEX_ARRAY[i] +"$1:$" + EXCEL_CELL_INDEX_ARRAY[i] + "$" + optionArray.length;
            DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
            // 判断最大行是否超过Excel最大值
            int maxRow = ExcelCommonUtil.EXCEL_MAX_ROW_NUMBER_2003 - ess.getFirstRowIndex();
            if(ess.getLastRowIndex() > maxRow){
                ess.setLastRowIndex(maxRow);
            }
            // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
            CellRangeAddressList regions = new CellRangeAddressList(ess.getFirstRowIndex(),ess.getLastRowIndex(), ess.getColumnIndex(), ess.getColumnIndex());
            HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
            if(ess.getSheet2003() != null){
                ess.getSheet2003().addValidationData(dataValidation);
            }
        }
        // 隐藏字典列表sheet
        workbook.setSheetHidden(sheetTotal, true);
    }

    /**
     * 设置Excel表格下拉选项-2007
     * 新建页签写入字典项引用的方式，解决直接写字典项字符过多报“tring literals in formulas can't be bigger than 255 characters ASCII”错误的问题
     *
     * @param workbook Excel工作簿对象
     * @param essList 下拉选项配置对象集合
     */
    public static void setWorkBookSelectOption(XSSFWorkbook workbook, List<ExcelSelectSetting> essList){
        if(workbook == null || CollectionUtils.isEmpty(essList)){
            return;
        }
        // 获取所有sheet页个数
        int sheetTotal = workbook.getNumberOfSheets();
        // 新建一个sheet页，写入字典数据
        String dictSheet = "dictSheet" + sheetTotal;
        XSSFSheet hiddenSheet = workbook.createSheet(dictSheet);

        for(int i=0; i<essList.size(); i++){
            ExcelSelectSetting ess = essList.get(i);
            String[] optionArray = ess.getOptionArray();

            if(optionArray == null){
                continue;
            }

            for(int j=0; j<optionArray.length; j++){
                // 获取行
                XSSFRow row = hiddenSheet.getRow(j);
                if(row == null){
                    row = hiddenSheet.createRow(j);
                }
                // 设置列值
                XSSFCell cell = row.createCell(i);
                String value = optionArray[j];
                cell.setCellValue(value);
            }
            // 设置模版字典值
            String strFormula = dictSheet + "!$" + EXCEL_CELL_INDEX_ARRAY[i] +"$1:$" + EXCEL_CELL_INDEX_ARRAY[i] + "$" + optionArray.length;
            XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(DataValidationConstraint.ValidationType.LIST, strFormula);
            // 判断最大行是否超过Excel最大值
            int maxRow = ExcelCommonUtil.EXCEL_MAX_ROW_NUMBER_2007 - ess.getFirstRowIndex();
            if(ess.getLastRowIndex() == ExcelCommonUtil.EXCEL_MAX_ROW_NUMBER_2003 ||
                    ess.getLastRowIndex() > maxRow){
                ess.setLastRowIndex(maxRow);
            }
            // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
            CellRangeAddressList regions = new CellRangeAddressList(ess.getFirstRowIndex(),ess.getLastRowIndex(), ess.getColumnIndex(), ess.getColumnIndex());
            if(ess.getSheet2007() != null){
                DataValidationHelper help = new XSSFDataValidationHelper(ess.getSheet2007());
                DataValidation validation = help.createValidation(constraint, regions);
                ess.getSheet2007().addValidationData(validation);
            }
        }
        // 隐藏字典列表sheet
        workbook.setSheetHidden(sheetTotal, true);
    }

    /**
     * 设置Excel页签某个字段下拉选项-2003
     * 下拉数据合计长度大于255会报错，使用本类“setWorkBookSelectOption”方法批量添加
     *
     * @param sheet Excel页签对象
     * @param firstRowIndex 需要设置下拉选项单元格首行下标，从0开始，默认为0
     * @param columnIndex 需要设置下拉选项单元格列下标，从0开始，默认为0
     * @param optionArray 下拉框数据数组
     */
    public static void setSheetSelectOption(HSSFSheet sheet, int firstRowIndex, int columnIndex, String[] optionArray) {
        CellRangeAddressList regions = new CellRangeAddressList(firstRowIndex, 65535, columnIndex, columnIndex);
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(optionArray);
        HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation);
    }

    /**
     * 设置Excel页签某个字段下拉选项-2007
     * 下拉数据合计长度大于255会报错，使用本类“setWorkBookSelectOption”方法批量添加
     *
     * @param sheet Excel页签对象
     * @param firstRowIndex 需要设置下拉选项单元格首行下标，从0开始，默认为0
     * @param columnIndex 需要设置下拉选项单元格列下标，从0开始，默认为0
     * @param optionArray 下拉框数据数组
     */
    public static void setSheetSelectOption(XSSFSheet sheet, int firstRowIndex, int columnIndex, String[] optionArray) {
        CellRangeAddressList regions = new CellRangeAddressList(firstRowIndex, 1048576, columnIndex, columnIndex);
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(optionArray);
        HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation);
    }

    /**
     * 下载Excel模版文件-2003
     *
     * @param response  Http响应对象
     * @param workbook Excel工作簿对象
     * @param downloadFileName 下载的文件名称（不带文件后缀）
     * @throws IOException
     */
    public static void downloadExcelTemplate(HttpServletResponse response, HSSFWorkbook workbook, String downloadFileName) throws IOException {
        if (response == null || workbook == null || StringUtils.isBlank(downloadFileName)) {
            return;
        }
        OutputStream outputStream = getOutputStream(response, downloadFileName, EXCEL_FILE_SUFFIX_VERSION_2003);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 下载Excel模版文件-2007
     *
     * @param response  Http响应对象
     * @param workbook Excel工作簿对象
     * @param downloadFileName 下载的文件名称（不带文件后缀）
     * @throws IOException
     */
    public static void downloadExcelTemplate(HttpServletResponse response, XSSFWorkbook workbook, String downloadFileName) throws IOException {
        if (response == null || workbook == null || StringUtils.isBlank(downloadFileName)) {
            return;
        }
        OutputStream outputStream = getOutputStream(response, downloadFileName, EXCEL_FILE_SUFFIX_VERSION_2007);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


}
