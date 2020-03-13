package ua.alexd.excelUtils.exports;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class RowsStylerBuilder {
    @Value("${excelExport.idColumnWidth}")
    private int idColumnWidth;
    @Value("${excelExport.dataColumnWidth}")
    private int dataColumnWidth;

    public RowsStyler getRowStyler(Workbook workbook) {
        return new RowsStyler(workbook, idColumnWidth, dataColumnWidth);
    }
}

public class RowsStyler {
    private CellStyle headerStyle;
    private CellStyle generalStyle;
    private Font headerFont;
    private Font generalFont;
    private final String fontName = "Lato";
    private final int idColumnWidth;
    private final int dataColumnWidth;

    private Workbook workbook;

    public RowsStyler(Workbook workbook, int idColumnWidth, int dataColumnWidth) {
        this.workbook = workbook;
        this.idColumnWidth = idColumnWidth;
        this.dataColumnWidth = dataColumnWidth;
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