package ua.alexd.excelView;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.ShopDomain;
import ua.alexd.domain.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.util.DateTimeProvider.getCurrentDateTime;

@Component("typeExcelView")
public class TypeExcelView extends AbstractXlsxView implements ExcelFileStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        List<ShopDomain> types = (List<ShopDomain>) model.get("types");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Type sheet");
        sheet.setFitToPage(true);
        setExcelHeader(sheet);
        setExcelRows(sheet, types);
        response.setHeader("Content-Disposition", "attachment; filename=type-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Sheet excelSheet) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Назва");
    }

    @Override
    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows) {
        var rowCount = 1;
        for (var row : rows) {
            var typeRow = (Type) row;
            var typeExcelRow = excelSheet.createRow(rowCount++);
            typeExcelRow.createCell(0).setCellValue(typeRow.getId());
            typeExcelRow.createCell(1).setCellValue(typeRow.getName());
        }
    }
}
