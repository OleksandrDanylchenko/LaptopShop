package ua.alexd.excelInteraction.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.alexd.domain.Buying;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service("buyingExcelView")
@Lazy
public class BuyingExcelExporter extends ExcelExporter {
    public BuyingExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        super(rowsStylerBuilder);
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Buying> buyings = (List<Buying>) model.get("buyings");
        var currentDateTime = timeProvider.getCurrentDateTime();
        var sheet = workbook.createSheet("Buyings sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, buyings, styler);

        response.setHeader("Content-Disposition", "attachment; filename=buyings-sheet " + currentDateTime + ".xlsx");
    }

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

    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Buying> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getTotalPrice());
            if (row.getBasket() == null) {
                generalRow.createCell(2).setCellValue("Видалено");
                generalRow.createCell(3).setCellValue("Видалено");
            } else {
                generalRow.createCell(2).setCellValue(row.getBasket().getId());
                generalRow.createCell(3).setCellValue(row.getBasket().getDateTime().toString());
            }
            if (row.getLaptop() == null) {
                generalRow.createCell(4).setCellValue("Видалено");
                generalRow.createCell(5).setCellValue("Видалено");
            } else {
                generalRow.createCell(4).setCellValue(row.getLaptop().getId());
                generalRow.createCell(5).setCellValue(row.getLaptop().getLabel().getModel());
            }
            styler.setGeneralRowStyle(generalRow);
        }
    }
}