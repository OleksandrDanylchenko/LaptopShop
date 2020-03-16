package ua.alexd.excelInteraction.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.dateTimeService.DateTimeProvider.getCurrentDateTime;

@Service("typeExcelView")
@Lazy
public class TypeExcelExporter extends AbstractXlsxView {
    private final RowsStylerBuilder rowsStylerBuilder;

    public TypeExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        this.rowsStylerBuilder = rowsStylerBuilder;
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Type> types = (List<Type>) model.get("types");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Types sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, types, styler);

        response.setHeader("Content-Disposition", "attachment; filename=types-sheet " + currentDateTime + ".xlsx");
    }


    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Назва");
        styler.setHeaderRowStyle(header, excelSheet);
    }


    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Type> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getName());
            styler.setGeneralRowStyle(generalRow);
        }
    }
}