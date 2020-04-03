package ua.alexd.excelInteraction.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.dateTimeService.DateTimeProvider.getCurrentDateTime;

@Service("employeeExcelView")
@Lazy
public class EmployeeExcelExporter extends AbstractXlsxView  {
    private final RowsStylerBuilder rowsStylerBuilder;

    public EmployeeExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        this.rowsStylerBuilder = rowsStylerBuilder;
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Employee> employees = (List<Employee>) model.get("employees");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Employees sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, employees, styler);

        response.setHeader("Content-Disposition", "attachment; filename=employees-sheet " + currentDateTime + ".xlsx");
    }


    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Ім'я");
        header.createCell(2).setCellValue("Прізвище");
        header.createCell(3).setCellValue("№ магазину");
        header.createCell(4).setCellValue("Адреса магазину");
        header.createCell(5).setCellValue("Статус");
        styler.setHeaderRowStyle(header, excelSheet);
    }


    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Employee> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getFirstName());
            generalRow.createCell(2).setCellValue(row.getSecondName());
            if (row.getShop() == null) {
                generalRow.createCell(3).setCellValue("Закрито");
                generalRow.createCell(4).setCellValue("Закрито");
            } else {
                generalRow.createCell(3).setCellValue(row.getShop().getId());
                generalRow.createCell(4).setCellValue(row.getShop().getAddress());
            }
            generalRow.createCell(5).setCellValue(row.getIsWorking() ? "Працюючий" : "Звільнений");
            styler.setGeneralRowStyle(generalRow);
        }
    }
}