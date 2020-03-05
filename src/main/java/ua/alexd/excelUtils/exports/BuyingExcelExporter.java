package ua.alexd.excelUtils.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Buying;
import ua.alexd.domain.ShopDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.dateTimeUtils.DateTimeProvider.getCurrentDateTime;

@Component("buyingExcelView")
public class BuyingExcelExporter extends AbstractXlsxView implements ExcelExportStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<ShopDomain> buyings = (List<ShopDomain>) model.get("buyings");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Buyings sheet");
        sheet.setFitToPage(true);

        var styler = new RowsStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, buyings, styler);

        response.setHeader("Content-Disposition", "attachment; filename=buyings-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Загальна ціна");
        header.createCell(2).setCellValue("Id кошика");
        header.createCell(3).setCellValue("Час покупки");
        header.createCell(4).setCellValue("Id ноутбуку");
        header.createCell(5).setCellValue("Модель ноутбуку");
        styler.setHeaderRowStyle(header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var buyingRow = (Buying) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(buyingRow.getId());
            generalRow.createCell(1).setCellValue(buyingRow.getTotalPrice());
            if (buyingRow.getBasket() == null) {
                generalRow.createCell(2).setCellValue("Видалено");
                generalRow.createCell(3).setCellValue("Видалено");
            } else {
                generalRow.createCell(2).setCellValue(buyingRow.getBasket().getId());
                generalRow.createCell(3).setCellValue(buyingRow.getBasket().getDateTime().toString());
            }
            if (buyingRow.getLaptop() == null) {
                generalRow.createCell(4).setCellValue("Видалено");
                generalRow.createCell(5).setCellValue("Видалено");
            } else {
                generalRow.createCell(4).setCellValue(buyingRow.getLaptop().getId());
                generalRow.createCell(5).setCellValue(buyingRow.getLaptop().getLabel().getModel());
            }
            styler.setGeneralRowStyle(generalRow);
        }
    }
}