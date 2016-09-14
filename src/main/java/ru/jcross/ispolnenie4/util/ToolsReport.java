package ru.jcross.ispolnenie4.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Chernyshov on 18.04.2016.
 */
public class ToolsReport {
    private Workbook getWorkbook(String excelFilePath)
            throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("Указанный файл, не является файлом Excel");
        }
        return workbook;
    }

    private void writeAttributes(Map<String, String> attribute, Sheet firstSheet) throws IOException {
        Iterator<Row> iterator = firstSheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                for (Map.Entry<String, String> entry : attribute.entrySet())
                {
                    if(getCellValue(cell).equals(entry.getKey())){
                        cell.setCellValue(entry.getValue());
                    }
                    System.out.println(entry.getKey() + " / " + entry.getValue());
                }
            }
        }
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue();

            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();

            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }

        return null;
    }

    public void writeExcel(ModelReport modelreport, String excelFilePath) throws IOException {
        Workbook workbook = getWorkbook(excelFilePath);
        Sheet sheet = workbook.createSheet();
        writeAttributes(modelreport.getAttribute(),sheet);
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }


}
