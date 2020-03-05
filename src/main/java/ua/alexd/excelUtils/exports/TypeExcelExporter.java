package ua.alexd.excelUtils.exports;

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

import static ua.alexd.dateTimeUtils.DateTimeProvider.getCurrentDateTime;

@Component("typeExcelView")
public class TypeExcelExporter extends AbstractXlsxView implements ExcelExportStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<ShopDomain> types = (List<ShopDomain>) model.get("types");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Types sheet");
        sheet.setFitToPage(true);

        var styler = new RowsStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, types, styler);

        response.setHeader("Content-Disposition", "attachment; filename=types-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Назва");
        styler.setHeaderRowStyle(header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var typeRow = (Type) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(typeRow.getId());
            generalRow.createCell(1).setCellValue(typeRow.getName());
            styler.setGeneralRowStyle(generalRow);
        }
    }
}