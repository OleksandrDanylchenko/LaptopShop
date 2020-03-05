package ua.alexd.excelUtils.exports;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;

import static ua.alexd.excelUtils.exports.ExcelExportStructure.dataColumnWidth;
import static ua.alexd.excelUtils.exports.ExcelExportStructure.idColumnWidth;

public class RowsStyler {
    private CellStyle headerStyle;
    private CellStyle generalStyle;
    private Font headerFont;
    private Font generalFont;
    private final String fontName = "Lato";

    private Workbook workbook;

    public RowsStyler(Workbook workbook) {
        this.workbook = workbook;
    }

    public void setHeaderRowStyle(Row headerRow, Sheet sheet) {
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

    public void setGeneralRowStyle(Row generalRow) {
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

    private void initializeHeaderStyle(Workbook workbook) {
        if (headerFont == null)
            initializeHeaderFont(workbook);
        if (generalStyle == null)
            initializeGeneralStyle(workbook);
        headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(generalStyle);
        headerStyle.setFont(headerFont);
        headerStyle.setRightBorderColor(IndexedColors.RED.getIndex());
        headerStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
        headerStyle.setTopBorderColor(IndexedColors.RED.getIndex());
        headerStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
    }

    private void initializeGeneralStyle(@NotNull Workbook workbook) {
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

    private void initializeHeaderFont(@NotNull Workbook workbook) {
        headerFont = workbook.createFont();
        headerFont.setFontName(fontName);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.RED.getIndex());
        headerFont.setFontHeightInPoints((short) 12);
    }

    private void initializeGeneralFont(@NotNull Workbook workbook) {
        generalFont = workbook.createFont();
        generalFont.setFontName(fontName);
    }
}