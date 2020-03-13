package ua.alexd.excelUtils.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Basket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.dateTimeUtils.DateTimeProvider.getCurrentDateTime;

@Component("basketExcelView")
public class BasketExcelExporter extends AbstractXlsxView {
    private final RowsStylerBuilder rowsStylerBuilder;

    public BasketExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        this.rowsStylerBuilder = rowsStylerBuilder;
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Basket> baskets = (List<Basket>) model.get("baskets");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Baskets sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, baskets, styler);

        response.setHeader("Content-Disposition", "attachment; filename=baskets-sheet " + currentDateTime + ".xlsx");
    }

    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
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
        styler.setHeaderRowStyle(header, excelSheet);
    }

    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Basket> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            if (row.getEmployee() == null) {
                generalRow.createCell(1).setCellValue("Видалено");
                generalRow.createCell(2).setCellValue("Видалено");
                generalRow.createCell(3).setCellValue("Видалено");
                generalRow.createCell(4).setCellValue("Видалено");
            } else {
                generalRow.createCell(1).setCellValue(row.getEmployee().getId());
                generalRow.createCell(2).setCellValue(row.getEmployee().getFirstName());
                generalRow.createCell(3).setCellValue(row.getEmployee().getSecondName());
                generalRow.createCell(4).setCellValue(row.getEmployee().getShop().getAddress());
            }
            if (row.getClient() == null) {
                generalRow.createCell(5).setCellValue("Видалено");
                generalRow.createCell(6).setCellValue("Видалено");
                generalRow.createCell(7).setCellValue("Видалено");
            } else {
                generalRow.createCell(5).setCellValue(row.getClient().getId());
                generalRow.createCell(6).setCellValue(row.getClient().getFirstName());
                generalRow.createCell(7).setCellValue(row.getClient().getSecondName());
            }
            generalRow.createCell(8).setCellValue(row.getDateTime().toString());
            styler.setGeneralRowStyle(generalRow);
        }
    }
}