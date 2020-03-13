package ua.alexd.excelUtils.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.dateTimeUtils.DateTimeProvider.getCurrentDateTime;

@Component("clientExcelView")
public class ClientExcelExporter extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Client> clients = (List<Client>) model.get("clients");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Clients sheet");
        sheet.setFitToPage(true);

        var styler = new RowsStylerBuilder().getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, clients, styler);

        response.setHeader("Content-Disposition", "attachment; filename=clients-sheet " + currentDateTime + ".xlsx");
    }

    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Ім'я");
        header.createCell(2).setCellValue("Прізвище");
        header.createCell(3).setCellValue("Дата реєстрації");
        styler.setHeaderRowStyle(header, excelSheet);
    }

    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Client> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getFirstName());
            generalRow.createCell(2).setCellValue(row.getSecondName());
            generalRow.createCell(3).setCellValue(row.getDateReg().toString());
            styler.setGeneralRowStyle(generalRow);
        }
    }
}