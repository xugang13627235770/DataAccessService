package org.springblade.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelImportUtil {

    /**
     * @param file  文件
     * @param cells 每行需要读取的列数  没有输入的列返回空串 ""
     * @return
     * @throws IOException
     */
    public static List<List<Object>> readExcel(MultipartFile file, int cells) throws IOException {
        String fileName = file.getOriginalFilename();
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            return read2003Excel(file, cells);
        } else if ("xlsx".equals(extension)) {
            return read2007Excel(file, cells);
        } else {
            throw new IOException("不支持的文件类型");
        }
    }

    /**
     * @param file  文件
     * @param cells 每行需要读取的列数  没有输入的列返回空串 ""
     * @return
     * @throws IOException
     */
    public static List<List<Object>> readBatchExcel(MultipartFile file, int cells, int count) throws IOException {
        String fileName = file.getOriginalFilename();
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            return batchRead2003Excel(file, cells, count);
        } else if ("xlsx".equals(extension)) {
            return batchRead2007Excel(file, cells, count);
        } else {
            throw new IOException("不支持的文件类型");
        }
    }

    /**
     * 读取 office 2003 excel
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static List<List<Object>> batchRead2003Excel(MultipartFile file, int cells, int count)
            throws IOException {
        List<List<Object>> list = new ArrayList<List<Object>>();
        HSSFWorkbook hwb = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = hwb.getSheetAt(count);
        Object value = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        int counter = 0;
        DecimalFormat df = new DecimalFormat("0");// 格式化 number String
        // 字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
        DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
        int num = sheet.getLastRowNum();
        for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            } else {
                counter++;
            }
            List<Object> Array = new ArrayList<Object>();
            int nullcells = 0;
            for (int j = row.getFirstCellNum(); j <= cells; j++) {
                cell = row.getCell(j);
                if (cell == null || "".equals(cell)) {
                    Array.add("");
                    nullcells++;
                    continue;
                }
                if (row.getCell(j) != null) {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            value = cell.getStringCellValue().trim();
                            if ("".equals(((String) value).trim())) {
                                nullcells++;
                            }
                            break;
                        case NUMERIC:
                            value = TransformUtils.DoubleToStr(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                        case BLANK:
                            value = "";
                            nullcells++;
                            break;
                        default:
                            value = cell.toString().trim();
                    }
                    Array.add(value);
                }
            }
            if (Array != null && nullcells != Array.size()) {
                list.add(Array);
            }
        }
        return list;
    }

    /**
     * 读取Office 2007 excel
     */
    private static List<List<Object>> batchRead2007Excel(MultipartFile file, int cells, int num) throws IOException {
        List<List<Object>> list = new ArrayList<>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream());
        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(num);
        Object value = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        int counter = 0;
        DecimalFormat df = new DecimalFormat("0");// 格式化 number String
        // 字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
        DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
        for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            } else {
                counter++;
            }
            List<Object> Array = new ArrayList<>();
            int nullcells = 0;
            for (int j = 0; j < cells; j++) {
                cell = row.getCell(j);
                if (cell == null) {
                    Array.add("");
                    nullcells++;
                    continue;
                }
                if (row.getCell(j) != null) {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            value = cell.getStringCellValue().trim();
                            if ("".equals(((String) value).trim())) {
                                nullcells++;
                            }
                            break;
                        case NUMERIC:
                            value = TransformUtils.DoubleToStr(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                        case BLANK:
                            value = "";
                            nullcells++;
                            break;
                        default:
                            value = cell.toString().trim();
                    }
                    Array.add(value);
                }
            }
            if (Array != null && nullcells < Array.size()) {
                list.add(Array);
            }
        }
        return list;
    }


    /**
     * 读取 office 2003 excel
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static List<List<Object>> read2003Excel(MultipartFile file, int cells)
            throws IOException {
        List<List<Object>> list = new ArrayList<List<Object>>();
        HSSFWorkbook hwb = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = hwb.getSheetAt(0);
        Object value = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        int counter = 0;
        for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            } else {
                counter++;
            }
            List<Object> Array = new ArrayList<Object>();
            int nullcells = 0;
            for (int j = row.getFirstCellNum(); j <= cells; j++) {
                cell = row.getCell(j);
                if (cell == null || "".equals(cell)) {
                    Array.add("");
                    nullcells++;
                    continue;
                }
                if (row.getCell(j) != null) {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            value = cell.getStringCellValue().trim();
                            if ("".equals(((String) value).trim())) {
                                nullcells++;
                            }
                            break;
                        case NUMERIC:
                            value = TransformUtils.DoubleToStr(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                        case BLANK:
                            value = "";
                            nullcells++;
                            break;
                        default:
                            value = cell.toString().trim();
                    }
                    Array.add(value);
                }
            }
            if (Array != null && nullcells != Array.size()) {
                list.add(Array);
            }
        }
        return list;
    }

    /**
     * 读取Office 2007 excel
     */
    private static List<List<Object>> read2007Excel(MultipartFile file, int cells) throws IOException {
        List<List<Object>> list = new ArrayList<>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream());
        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        Object value = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        int counter = 0;
        DecimalFormat df = new DecimalFormat("0");// 格式化 number String
        // 字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
        DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
        for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            } else {
                counter++;
            }
            List<Object> Array = new ArrayList<>();
            int nullcells = 0;
            for (int j = 0; j < cells; j++) {
                cell = row.getCell(j);
                if (cell == null) {
                    Array.add("");
                    nullcells++;
                    continue;
                }
                if (row.getCell(j) != null) {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            value = cell.getStringCellValue().trim();
                            if ("".equals(((String) value).trim())) {
                                nullcells++;
                            }
                            break;
                        case NUMERIC:
                            value = TransformUtils.DoubleToStr(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                        case BLANK:
                            value = "";
                            nullcells++;
                            break;
                        default:
                            value = cell.toString().trim();
                    }
                    Array.add(value);
                }
            }
            if (Array != null && nullcells < Array.size()) {
                list.add(Array);
            }
        }
        return list;
    }

}
