package ua.alexd.excelView.export;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.domain.Laptop;
import ua.alexd.domain.ShopDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.alexd.excelView.export.RowStyleProvider.*;
import static ua.alexd.util.DateTimeProvider.getCurrentDateTime;

@Component("laptopExcelView")
public class LaptopExcelExporter extends AbstractXlsxView implements ExcelExportStructure {
    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        List<ShopDomain> types = (List<ShopDomain>) model.get("laptops");
        var currentDateTime = getCurrentDateTime();
        var sheet = workbook.createSheet("Laptops sheet");
        sheet.setFitToPage(true);

        wipePreviousStyles();
        setExcelHeader(workbook, sheet);
        setExcelRows(workbook, sheet, types);

        response.setHeader("Content-Disposition", "attachment; filename=laptops-sheet " + currentDateTime + ".xlsx");
    }

    @Override
    public void setExcelHeader(@NotNull Workbook workbook, @NotNull Sheet excelSheet) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Бренд");
        header.createCell(2).setCellValue("Модель");
        header.createCell(3).setCellValue("Тип");
        header.createCell(4).setCellValue("Збірка");
        setHeaderRowStyle(workbook, header, excelSheet);
    }

    @Override
    public void setExcelRows(@NotNull Workbook workbook, @NotNull Sheet excelSheet, @NotNull List<ShopDomain> rows) {
        var rowCount = 1;
        for (var row : rows) {
            var laptopRow = (Laptop) row;
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(laptopRow.getId());
            generalRow.createCell(1).setCellValue(laptopRow.getLabel().getBrand());
            generalRow.createCell(2).setCellValue(laptopRow.getLabel().getModel());
            generalRow.createCell(3).setCellValue(laptopRow.getType() != null
                    ? laptopRow.getType().getName()
                    : "Відсутній");
            generalRow.createCell(4).setCellValue(laptopRow.getHardware() != null
                    ? laptopRow.getHardware().getAssemblyName()
                    : "Відсутній");
            setGeneralRowStyle(workbook, generalRow);
        }
    }
}