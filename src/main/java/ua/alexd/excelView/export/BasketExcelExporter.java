package ua.alexd.excelView.export;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Basket;
import ua.alexd.domain.ShopDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.excelView.export.RowStyleProvider.*;
import static ua.alexd.util.DateTimeProvider.getCurrentDateTime;

@Component("basketExcelView")
public class BasketExcelExporter extends AbstractXlsxView implements ExcelExportStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        List<ShopDomain> baskets = (List<ShopDomain>) model.get("baskets");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Baskets sheet");
        sheet.setFitToPage(true);

        wipePreviousStyles();
        setExcelHeader(workbook, sheet);
        setExcelRows(workbook, sheet, baskets);

        response.setHeader("Content-Disposition", "attachment; filename=baskets-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Workbook workbook, @NotNull Sheet excelSheet) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Id продавця");
        header.createCell(2).setCellValue("Ім'я продавця");
        header.createCell(3).setCellValue("Прізвище продавця");
        header.createCell(4).setCellValue("Адреса магазину");
        header.createCell(5).setCellValue("Id покупця");
        header.createCell(6).setCellValue("Ім'я покупця");
        header.createCell(7).setCellValue("Прізвище покупця");
        header.createCell(8).setCellValue("Час покупки");
        setHeaderRowStyle(workbook, header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Workbook workbook, @NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows) {
        var rowCount = 1;
        for (var row : rows) {
            var basketRow = (Basket) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(basketRow.getId());
            if (basketRow.getEmployee() == null) {
                generalRow.createCell(1).setCellValue("Видалено");
                generalRow.createCell(2).setCellValue("Видалено");
                generalRow.createCell(3).setCellValue("Видалено");
                generalRow.createCell(4).setCellValue("Видалено");
            } else {
                generalRow.createCell(1).setCellValue(basketRow.getEmployee().getId());
                generalRow.createCell(2).setCellValue(basketRow.getEmployee().getFirstName());
                generalRow.createCell(3).setCellValue(basketRow.getEmployee().getSecondName());
                generalRow.createCell(4).setCellValue(basketRow.getEmployee().getShop().getAddress());
            }
            if (basketRow.getClient() == null) {
                generalRow.createCell(5).setCellValue("Видалено");
                generalRow.createCell(6).setCellValue("Видалено");
                generalRow.createCell(7).setCellValue("Видалено");
            } else {
                generalRow.createCell(5).setCellValue(basketRow.getClient().getId());
                generalRow.createCell(6).setCellValue(basketRow.getClient().getFirstName());
                generalRow.createCell(7).setCellValue(basketRow.getClient().getSecondName());
            }
            generalRow.createCell(8).setCellValue(basketRow.getDateTime().toString());
            setGeneralRowStyle(workbook, generalRow);
        }
    }
}