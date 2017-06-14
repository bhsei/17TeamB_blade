package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

/**
 * Created this one by HMH on 2017/6/13.
 */
public class ExcelUtil {

    public static void SetHeaders(HSSFSheet sheet, String... headers) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
    }

    public static void SetHeader(HSSFSheet sheet,String header, int index) {
        getCell(sheet,0,index).setCellValue(header);
    }

    public static void setStringColumn(HSSFSheet sheet, int index, boolean keepHeader, String... colValues) {
        int start = keepHeader?1:0;
        int i=0;
        while(i<colValues.length){
            getCell(sheet,start++,index).setCellValue(colValues[i++]);
        }
    }

    public static void setIntegerColumn(HSSFSheet sheet, int index, boolean keepHeader, int... colValues) {
        int start = keepHeader?1:0;
        int i=0;
        while(i<colValues.length){
            getCell(sheet,start++,index).setCellValue(colValues[i++]);
        }
    }

    public static void setDoubleColumn(HSSFSheet sheet, int index, boolean keepHeader, double... colValues) {
        int start = keepHeader?1:0;
        int i=0;
        while(i<colValues.length){
            getCell(sheet,start++,index).setCellValue(colValues[i++]);
        }
    }

    public static <T> void addRow(HSSFSheet sheet, int index, boolean keepHeader, T... rowValues) {
        int start = keepHeader ? 1 : 0;
        int i=0;
        while (i < rowValues.length) {
            getCell(sheet,index,start++).setCellValue(String.valueOf(rowValues[i++]));
        }
    }

    public static HSSFRow getRow(HSSFSheet sheet,int index){
        HSSFRow row = sheet.getRow(index);
        if (row == null) {
            row = sheet.createRow(index);
        }
        return row;
    }

    public static HSSFCell getCell(HSSFRow row,int index){

        HSSFCell cell = row.getCell(index);
        if (cell == null) {
            cell = row.createCell(index);
        }
        return cell;
    }

    public static HSSFCell getCell(HSSFSheet sheet, int rowIndex, int colIndex) {
        return getCell(getRow(sheet, rowIndex), colIndex);
    }

    public static HSSFSheet createSheet(HSSFWorkbook workbook, String name,String... headers) {
        HSSFSheet sheet = workbook.createSheet(name);
        if (headers != null && headers.length!=0) {
            SetHeaders(sheet,headers);
        }
        return sheet;
    }

    public static HSSFSheet getSheet(HSSFWorkbook workbook, int index) {
        return workbook.getSheetAt(index);
    }

    public static int getRowLength(HSSFSheet sheet) {
        return sheet.getPhysicalNumberOfRows();
    }

    public static int getColLength(HSSFSheet sheet) {
        return getRow(sheet, 0).getPhysicalNumberOfCells();
    }

    public static HSSFWorkbook readExcel(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()) throw new FileNotFoundException("没有找到该文件");
        FileInputStream fileInputStream = new FileInputStream(file);
        return new HSSFWorkbook(fileInputStream);
    }

    public static HSSFWorkbook createExcel() {
        return new HSSFWorkbook();
    }

    public static File saveToFile(HSSFWorkbook workbook,String path) throws IOException {
        File file = new File(path);
        File dic = file.getParentFile();
        if(!dic.exists()) dic.mkdirs();
        workbook.write(file);
        return file;
    }

}
