package org.springblade.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

public class ExcelCommonUtil {

    /**
     * Excel文件后缀-2003版本
     */
    public static final String EXCEL_FILE_SUFFIX_VERSION_2003 = ".xls";

    /**
     * Excel文件最大行数-2003版本
     */
    public static final int EXCEL_MAX_ROW_NUMBER_2003 = 65535;

    /**
     * Excel文件后缀-2007版本
     */
    public static final String EXCEL_FILE_SUFFIX_VERSION_2007 = ".xlsx";

    /**
     * Excel文件最大行数-2007版本
     */
    public static final int EXCEL_MAX_ROW_NUMBER_2007 = 1048576;



    /**
     * 获取Http相应对象输出流
     *
     * @param response Http响应对象
     * @param downloadFileName 下载的文件名称（不带文件后缀）
     * @param suffix 下载的文件后缀
     * @return
     * @throws IOException
     */
    public static OutputStream getOutputStream(HttpServletResponse response, String downloadFileName, String suffix) throws IOException{
        //转码，免得文件名中文乱码
        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + downloadFileName + suffix);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        return response.getOutputStream();
    }

    /**
     * 获取表头样式-2003
     *
     * @param headStyle 单元格样式对象
     * @param headFont 字体对象
     * @return
     */
    public static HSSFCellStyle getExcelHeadStyle(HSSFCellStyle headStyle, HSSFFont headFont){
        // 设置粗体
        headFont.setBold(true);
        // 设置字号
        headFont.setFontHeightInPoints((short)12);
        headStyle.setFont(headFont);
        // 设置边框
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        // 设置背景色
        headStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 水平居中
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 自动换行
        headStyle.setWrapText(true);

        return headStyle;
    }

    /**
     * 获取表头样式-2007
     *
     * @param headStyle 单元格样式对象
     * @param headFont 字体对象
     * @return
     */
    public static XSSFCellStyle getExcelHeadStyle(XSSFCellStyle headStyle, XSSFFont headFont){
        // 设置粗体
        headFont.setBold(true);
        // 设置字号
        headFont.setFontHeightInPoints((short)12);
        headStyle.setFont(headFont);
        // 设置边框
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        // 设置背景色
        headStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 水平居中
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 自动换行
        headStyle.setWrapText(true);

        return headStyle;
    }
}
