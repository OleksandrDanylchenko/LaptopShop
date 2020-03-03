package ua.alexd.excelView.export;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Client;
import ua.alexd.domain.ShopDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.excelView.export.RowStyleProvider.*;
import static ua.alexd.util.DateTimeProvider.getCurrentDateTime;

@Component("clientExcelView")
public class ClientExcelExporter extends AbstractXlsxView implements ExcelExportStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        List<ShopDomain> clients = (List<ShopDomain>) model.get("clients");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Clients sheet");
        sheet.setFitToPage(true);

        wipePreviousStyles();
        setExcelHeader(workbook, sheet);
        setExcelRows(workbook, sheet, clients);

        response.setHeader("Content-Disposition", "attachment; filename=clients-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Workbook workbook, @NotNull Sheet excelSheet) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Ім'я");
        header.createCell(2).setCellValue("Прізвище");
        header.createCell(3).setCellValue("Дата реєстрації");
        setHeaderRowStyle(workbook, header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Workbook workbook, @NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows) {
        var rowCount = 1;
        for (var row : rows) {
            var clientRow = (Client) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(clientRow.getId());
            generalRow.createCell(1).setCellValue(clientRow.getFirstName());
            generalRow.createCell(2).setCellValue(clientRow.getSecondName());
            generalRow.createCell(3).setCellValue(clientRow.getDateReg().toString());
            setGeneralRowStyle(workbook, generalRow);
        }
    }
}