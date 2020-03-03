package ua.alexd.excelUtils.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Display;
import ua.alexd.domain.ShopDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.excelUtils.exports.RowStyleProvider.*;
import static ua.alexd.dateTimeUtils.DateTimeProvider.getCurrentDateTime;

@Component("displayExcelView")
public class DisplayExcelExporter extends AbstractXlsxView implements ExcelExportStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        List<ShopDomain> displays = (List<ShopDomain>) model.get("displays");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Displays sheet");
        sheet.setFitToPage(true);

        wipePreviousStyles();
        setExcelHeader(workbook, sheet);
        setExcelRows(workbook, sheet, displays);

        response.setHeader("Content-Disposition", "attachment; filename=displays-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Workbook workbook, @NotNull Sheet excelSheet) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Модель");
        header.createCell(2).setCellValue("Тип");
        header.createCell(3).setCellValue("Діагональ");
        header.createCell(4).setCellValue("Розширення");
        setHeaderRowStyle(workbook, header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Workbook workbook, @NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows) {
        var rowCount = 1;
        for (var row : rows) {
            var displayRow = (Display) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(displayRow.getId());
            generalRow.createCell(1).setCellValue(displayRow.getModel());
            generalRow.createCell(2).setCellValue(displayRow.getType());
            generalRow.createCell(3).setCellValue(displayRow.getDiagonal());
            generalRow.createCell(4).setCellValue(displayRow.getResolution());
            setGeneralRowStyle(workbook, generalRow);
        }
    }
}