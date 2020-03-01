package ua.alexd.excelView;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;

import static ua.alexd.excelView.ExcelFileStructure.dataColumnWidth;
import static ua.alexd.excelView.ExcelFileStructure.idColumnWidth;

public class rowStyleProvider {
    private static CellStyle headerStyle = null;
    private static CellStyle generalStyle = null;
    private static Font headerFont = null;
    private static Font generalFont = null;
    private static String fontName = "Lato";

    public static void setHeaderRowStyle(Workbook workbook, Row headerRow, Sheet sheet) {
        if (headerStyle == null)
            initializeHeaderStyle(workbook);
        int i = 0;
        for (var cell : headerRow) {
            if (i == 0)
                sheet.setColumnWidth(i, idColumnWidth);
            else
                sheet.setColumnWidth(i, dataColumnWidth);
            cell.setCellStyle(headerStyle);
            ++i;
        }
    }

    public static void setGeneralRowStyle(Workbook workbook, Row generalRow) {
        if (generalStyle == null)
            initializeGeneralStyle(workbook);
        int i = 0;
        for (var cell : generalRow) {
            if (i == 0)
                cell.setCellStyle(headerStyle);
            else
                cell.setCellStyle(generalStyle);
            ++i;
        }
    }

    private static void initializeHeaderStyle(Workbook workbook) {
        if (generalStyle == null)
            initializeGeneralStyle(workbook);
        if (headerFont == null)
            initializeHeaderFont(workbook);
        headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(generalStyle);
        headerStyle.setFont(headerFont);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setRightBorderColor(IndexedColors.RED.getIndex());
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setTopBorderColor(IndexedColors.RED.getIndex());
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
    }

    private static void initializeGeneralStyle(@NotNull Workbook workbook) {
        if (generalFont == null)
            initializeGeneralFont(workbook);
        generalStyle = workbook.createCellStyle();
        generalStyle.setFont(generalFont);
        generalStyle.setWrapText(true);
        generalStyle.setAlignment(HorizontalAlignment.CENTER);
        generalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    private static void initializeHeaderFont(@NotNull Workbook workbook) {
        headerFont = workbook.createFont();
        headerFont.setFontName(fontName);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.RED.getIndex());
        headerFont.setFontHeightInPoints((short) 10);
    }

    private static void initializeGeneralFont(@NotNull Workbook workbook) {
        generalFont = workbook.createFont();
        generalFont.setFontName(fontName);
    }

    public static void wipePreviousStyles() {
        headerStyle = generalStyle = null;
        headerFont = generalFont = null;
    }
}