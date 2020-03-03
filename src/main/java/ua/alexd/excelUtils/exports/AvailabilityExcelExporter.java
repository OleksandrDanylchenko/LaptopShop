package ua.alexd.excelUtils.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Availability;
import ua.alexd.domain.ShopDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.excelUtils.exports.RowStyleProvider.*;
import static ua.alexd.dateTimeUtils.DateTimeProvider.getCurrentDateTime;

@Component("availabilityExcelView")
public class AvailabilityExcelExporter extends AbstractXlsxView implements ExcelExportStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        List<ShopDomain> availabilities = (List<ShopDomain>) model.get("availabilities");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Availability sheet");
        sheet.setFitToPage(true);

        wipePreviousStyles();
        setExcelHeader(workbook, sheet);
        setExcelRows(workbook, sheet, availabilities);

        response.setHeader("Content-Disposition", "attachment; filename=availability-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Workbook workbook, @NotNull Sheet excelSheet) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Модель");
        header.createCell(2).setCellValue("Ціна");
        header.createCell(3).setCellValue("Кількість");
        header.createCell(4).setCellValue("Номер магазину");
        header.createCell(5).setCellValue("Адреса магазину");
        header.createCell(6).setCellValue("Початок продаж");
        header.createCell(7).setCellValue("Закінчення продаж<");
        setHeaderRowStyle(workbook, header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Workbook workbook, @NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows) {
        var rowCount = 1;
        for (var row : rows) {
            var availabilityRow = (Availability) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(availabilityRow.getId());
            generalRow.createCell(1).setCellValue(availabilityRow.getLaptop().getLabel().getModel());
            generalRow.createCell(2).setCellValue(availabilityRow.getPrice());
            generalRow.createCell(3).setCellValue(availabilityRow.getQuantity());
            generalRow.createCell(4).setCellValue(availabilityRow.getShop().getId());
            generalRow.createCell(5).setCellValue(availabilityRow.getShop().getAddress());
            generalRow.createCell(6).setCellValue(availabilityRow.getDateStart().toString());
            generalRow.createCell(7).setCellValue(availabilityRow.getDateEnd().toString());
            setGeneralRowStyle(workbook, generalRow);
        }
    }
}