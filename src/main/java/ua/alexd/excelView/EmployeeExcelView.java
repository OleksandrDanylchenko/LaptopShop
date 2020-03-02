package ua.alexd.excelView;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Employee;
import ua.alexd.domain.ShopDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.excelView.RowStyleProvider.*;
import static ua.alexd.util.DateTimeProvider.getCurrentDateTime;

@Component("employeeExcelView")
public class EmployeeExcelView extends AbstractXlsxView implements ExcelFileStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        List<ShopDomain> types = (List<ShopDomain>) model.get("employees");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Employees sheet");
        sheet.setFitToPage(true);

        wipePreviousStyles();
        setExcelHeader(workbook, sheet);
        setExcelRows(workbook, sheet, types);

        response.setHeader("Content-Disposition", "attachment; filename=employees-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Workbook workbook, @NotNull Sheet excelSheet) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Ім'я");
        header.createCell(2).setCellValue("Прізвище");
        header.createCell(3).setCellValue("№ магазину");
        header.createCell(4).setCellValue("Адреса магазину");
        header.createCell(5).setCellValue("Статус");
        setHeaderRowStyle(workbook, header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Workbook workbook, @NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows) {
        var rowCount = 1;
        for (var row : rows) {
            var employeeRow = (Employee) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(employeeRow.getId());
            generalRow.createCell(1).setCellValue(employeeRow.getFirstName());
            generalRow.createCell(2).setCellValue(employeeRow.getSecondName());
            if (employeeRow.getShop() == null) {
                generalRow.createCell(3).setCellValue("Закрито");
                generalRow.createCell(4).setCellValue("Закрито");
            } else {
                generalRow.createCell(3).setCellValue(employeeRow.getShop().getId());
                generalRow.createCell(4).setCellValue(employeeRow.getShop().getAddress());
            }
            generalRow.createCell(5).setCellValue(employeeRow.getIsActive() ? "Працюючий" : "Звільнений");
            setGeneralRowStyle(workbook, generalRow);
        }
    }
}