package org.springblade.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springblade.common.vo.ExcelExportSetting;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 导出Excel工具类
 */
public class ExcelExportUtil extends ExcelCommonUtil {

    /**
     * 获取Excel工作簿单页签--2003
     *
     * @param ees 导出参数对象
     * @return
     */
    public static HSSFWorkbook getWorkbookTo2003(ExcelExportSetting ees){
        if(ees == null || ees.getCellTitleArray() == null || ees.getCellTitleArray().length < 1 || CollectionUtils.isEmpty(ees.getDataList())){
            return null;
        }
        // 声明一个工作薄
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workBook.createSheet();
        // 设置第一个表格名称
        workBook.setSheetName(0, ees.getSheetName());
        // 添加数据到Excel工作簿
        addDataToWorkbook(workBook, sheet, ees.getCellTitleArray(), ees.getDataList());
        return workBook;
    }

    /**
     * 获取Excel工作簿多页签--2003
     *
     * @param eesList 导出参数对象集合
     * @return
     */
    public static HSSFWorkbook getWorkbookTo2003(List<ExcelExportSetting> eesList){
        if(CollectionUtils.isEmpty(eesList)){
            return null;
        }
        // 声明一个工作薄
        HSSFWorkbook workBook = new HSSFWorkbook();

        for(int i=0; i<eesList.size(); i++){
            ExcelExportSetting ees = eesList.get(i);
            if(ees == null || ees.getCellTitleArray() == null || ees.getCellTitleArray().length < 1 || CollectionUtils.isEmpty(ees.getDataList())){
                continue;
            }
            // 生成一个表格
            HSSFSheet sheet = workBook.createSheet();
            // 设置第一个表格名称
            workBook.setSheetName(i, ees.getSheetName());
            // 添加数据到Excel工作簿
            addDataToWorkbook(workBook, sheet, ees.getCellTitleArray(), ees.getDataList());
        }
        return workBook;
    }

    /**
     * 获取Excel工作簿单页签--2007
     *
     * @param ees 导出参数对象
     * @return
     */
    public static XSSFWorkbook getWorkbookTo2007(ExcelExportSetting ees){
        if(ees == null || ees.getCellTitleArray() == null || ees.getCellTitleArray().length < 1 || CollectionUtils.isEmpty(ees.getDataList())){
            return null;
        }
        // 声明一个工作薄
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workBook.createSheet();
        // 设置第一个表格名称
        workBook.setSheetName(0, ees.getSheetName());
        // 添加数据到Excel工作簿
        addDataToWorkbook(workBook, sheet, ees.getCellTitleArray(), ees.getDataList());
        return workBook;
    }

    /**
     * 获取Excel工作簿多页签--2007
     *
     * @param eesList 导出参数对象集合
     * @return
     */
    public static XSSFWorkbook getWorkbookTo2007(List<ExcelExportSetting> eesList){
        if(CollectionUtils.isEmpty(eesList)){
            return null;
        }
        // 声明一个工作薄
        XSSFWorkbook workBook = new XSSFWorkbook();
        for(int i=0; i<eesList.size(); i++) {
            ExcelExportSetting ees = eesList.get(i);
            if (ees == null || ees.getCellTitleArray() == null || ees.getCellTitleArray().length < 1 || CollectionUtils.isEmpty(ees.getDataList())) {
                continue;
            }
            // 生成一个表格
            XSSFSheet sheet = workBook.createSheet();
            // 设置第一个表格名称
            workBook.setSheetName(i, ees.getSheetName());
            // 添加数据到Excel工作簿
            addDataToWorkbook(workBook, sheet, ees.getCellTitleArray(), ees.getDataList());
        }
        return workBook;
    }

    /**
     * 下载Excel工作簿单页签-2003
     *
     * @param ees 导出参数对象
     * @param response Http响应对象
     * @param downloadFileName 下载文件名称（不带后缀）
     * @throws IOException
     */
    public static void downloadExcelTo2003(ExcelExportSetting ees, HttpServletResponse response, String downloadFileName) throws IOException {
        if(ees == null || ees.getCellTitleArray() == null || ees.getCellTitleArray().length < 1 || CollectionUtils.isEmpty(ees.getDataList())){
            return;
        }
        // 获取Excel工作簿-2007
        HSSFWorkbook workBook = getWorkbookTo2003(ees);
        if(workBook == null){
            return;
        }
        // 转码，免得文件名中文乱码
        if(StringUtils.isBlank(downloadFileName)){
            downloadFileName = "Excel";
        }
        OutputStream outputStream = getOutputStream(response, downloadFileName, EXCEL_FILE_SUFFIX_VERSION_2003);
        workBook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 下载Excel工作簿多页签-2003
     *
     * @param eesList 导出参数对象集合
     * @param response Http响应对象
     * @param downloadFileName 下载文件名称（不带后缀）
     * @throws IOException
     */
    public static void downloadExcelTo2003(List<ExcelExportSetting> eesList, HttpServletResponse response, String downloadFileName) throws IOException {
        if(CollectionUtils.isEmpty(eesList)){
            return;
        }
        // 获取Excel工作簿-2007
        HSSFWorkbook workBook = getWorkbookTo2003(eesList);
        if(workBook == null){
            return;
        }
        // 转码，免得文件名中文乱码
        if(StringUtils.isBlank(downloadFileName)){
            downloadFileName = "Excel";
        }
        OutputStream outputStream = getOutputStream(response, downloadFileName, EXCEL_FILE_SUFFIX_VERSION_2003);
        workBook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 下载Excel工作簿单页签--2007
     *
     * @param ees 导出参数对象
     * @param response Http响应对象
     * @param downloadFileName 下载文件名称（不带后缀）
     * @throws IOException
     */
    public static void downloadExcelTo2007(ExcelExportSetting ees, HttpServletResponse response, String downloadFileName) throws IOException {
        if(ees == null || ees.getCellTitleArray() == null || ees.getCellTitleArray().length < 1 || CollectionUtils.isEmpty(ees.getDataList())){
            return;
        }
        // 获取Excel工作簿-2007
        XSSFWorkbook workBook = getWorkbookTo2007(ees);
        if(workBook == null){
            return;
        }
        // 转码，免得文件名中文乱码
        if(StringUtils.isBlank(downloadFileName)){
            downloadFileName = "Excel";
        }
        OutputStream outputStream = getOutputStream(response, downloadFileName, EXCEL_FILE_SUFFIX_VERSION_2007);
        workBook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 下载Excel工作簿多页签--2007
     *
     * @param eesList 导出参数对象集合
     * @param response Http响应对象
     * @param downloadFileName 下载文件名称（不带后缀）
     * @throws IOException
     */
    public static void downloadExcelTo2007(List<ExcelExportSetting> eesList, HttpServletResponse response, String downloadFileName) throws IOException {
        if(CollectionUtils.isEmpty(eesList)){
            return;
        }
        // 获取Excel工作簿-2007
        XSSFWorkbook workBook = getWorkbookTo2007(eesList);
        if(workBook == null){
            return;
        }
        // 转码，免得文件名中文乱码
        if(StringUtils.isBlank(downloadFileName)){
            downloadFileName = "Excel";
        }
        OutputStream outputStream = getOutputStream(response, downloadFileName, EXCEL_FILE_SUFFIX_VERSION_2007);
        workBook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 添加数据到Excel工作簿-2003
     *
     * @param workBook Excel工作簿对象
     * @param sheet 页签对象
     * @param cellTitle 表头名称数组
     * @param dataList 行数据数组集合
     */
    private static void addDataToWorkbook(HSSFWorkbook workBook, HSSFSheet sheet, String [] cellTitle, List<String[]> dataList){
        // 表头样式
        HSSFCellStyle headStyle = getExcelHeadStyle(workBook.createCellStyle(), workBook.createFont());
        // 创建表格表头 第一行
        HSSFRow titleRow = sheet.createRow(0);
        // 填充表头
        for(int i=0; i<cellTitle.length; i++){
            HSSFCell cell = titleRow.createCell(i);
            cell.setCellStyle(headStyle);
            String value = cellTitle[i];
            cell.setCellValue(value);
            // 设置列宽
            sheet.setColumnWidth(i, 5000);
        }
        //插入需导出的数据
        for(int i=0; i<dataList.size(); i++){
            String[] dataRow = dataList.get(i);
            if(dataRow != null && dataRow.length > 0){
                HSSFRow row = sheet.createRow(i + 1);
                for(int j=0; j<dataRow.length; j++){
                    row.createCell(j).setCellValue(dataRow[j]);
                }
            }
        }
    }

    /**
     * 添加数据到Excel工作簿-2007
     *
     * @param workBook Excel工作簿对象
     * @param sheet 页签对象
     * @param cellTitle 表头名称数组
     * @param dataList 行数据数组集合
     */
    private static void addDataToWorkbook(XSSFWorkbook workBook, XSSFSheet sheet, String [] cellTitle, List<String[]> dataList){
        // 表头样式
        XSSFCellStyle headStyle = getExcelHeadStyle(workBook.createCellStyle(), workBook.createFont());
        // 创建表格表头 第一行
        XSSFRow titleRow = sheet.createRow(0);
        // 填充表头
        for(int i=0; i<cellTitle.length; i++){
            XSSFCell cell = titleRow.createCell(i);
            cell.setCellStyle(headStyle);
            String value = cellTitle[i];
            cell.setCellValue(value);
            // 设置列宽
            sheet.setColumnWidth(i, 5000);
        }
        //插入需导出的数据
        for(int i=0; i<dataList.size(); i++){
            String[] dataRow = dataList.get(i);
            if(dataRow != null && dataRow.length > 0){
                XSSFRow row = sheet.createRow(i + 1);
                for(int j=0; j<dataRow.length; j++){
                    row.createCell(j).setCellValue(dataRow[j]);
                }
            }
        }
    }

}
