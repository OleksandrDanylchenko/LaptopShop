package ua.alexd.excelUtils.exports;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.CPU;
import ua.alexd.domain.ShopDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.dateTimeUtils.DateTimeProvider.getCurrentDateTime;

@Component("cpuExcelView")
public class CPUExcelExporter extends AbstractXlsxView implements ExcelExportStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<ShopDomain> cpus = (List<ShopDomain>) model.get("cpus");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("CPUs sheet");
        sheet.setFitToPage(true);

        var styler = new RowsStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, cpus, styler);

        response.setHeader("Content-Disposition", "attachment; filename=cpus-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Модель");
        header.createCell(2).setCellValue("Частота(GHz)");
        styler.setHeaderRowStyle(header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var cpuRow = (CPU) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(cpuRow.getId());
            generalRow.createCell(1).setCellValue(cpuRow.getModel());
            generalRow.createCell(2).setCellValue(cpuRow.getFrequency());
            styler.setGeneralRowStyle(generalRow);
        }
    }
}