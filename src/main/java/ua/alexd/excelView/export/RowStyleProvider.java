package ua.alexd.excelView.export;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;

import static ua.alexd.excelView.export.ExcelExportStructure.dataColumnWidth;
import static ua.alexd.excelView.export.ExcelExportStructure.idColumnWidth;

public class RowStyleProvider {
    private static CellStyle headerStyle;
    private static CellStyle generalStyle;
    private static Font headerFont;
    private static Font generalFont;
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
        if (headerFont == null)
            initializeHeaderFont(workbook);
        if (generalStyle == null)
            initializeGeneralStyle(workbook);
        headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.cloneStyleFrom(generalStyle);
        headerStyle.setRightBorderColor(IndexedColors.RED.getIndex());
        headerStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
        headerStyle.setTopBorderColor(IndexedColors.RED.getIndex());
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
        generalStyle.setBorderRight(BorderStyle.THIN);
        generalStyle.setBorderLeft(BorderStyle.THIN);
        generalStyle.setBorderTop(BorderStyle.THIN);
        generalStyle.setBorderBottom(BorderStyle.THIN);
    }

    private static void initializeHeaderFont(@NotNull Workbook workbook) {
        headerFont = workbook.createFont();
        headerFont.setFontName(fontName);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.RED.getIndex());
        headerFont.setFontHeightInPoints((short) 12);
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